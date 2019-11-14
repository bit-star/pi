package com.lazulite.pi.web.rest;

import com.lazulite.pi.PiApp;
import com.lazulite.pi.domain.DdMessage;
import com.lazulite.pi.repository.DdMessageRepository;
import com.lazulite.pi.service.DdMessageService;
import com.lazulite.pi.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.lazulite.pi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lazulite.pi.domain.enumeration.DdMessageType;
/**
 * Integration tests for the {@link DdMessageResource} REST controller.
 */
@SpringBootTest(classes = PiApp.class)
public class DdMessageResourceIT {

    private static final String DEFAULT_RECEIVING_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVING_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVING_USER = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVING_USER = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_JSON = "AAAAAAAAAA";
    private static final String UPDATED_JSON = "BBBBBBBBBB";

    private static final Instant DEFAULT_SEND_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEND_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final DdMessageType DEFAULT_TYPE = DdMessageType.ActionCard;
    private static final DdMessageType UPDATED_TYPE = DdMessageType.Markdown;

    @Autowired
    private DdMessageRepository ddMessageRepository;

    @Autowired
    private DdMessageService ddMessageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDdMessageMockMvc;

    private DdMessage ddMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DdMessageResource ddMessageResource = new DdMessageResource(ddMessageService);
        this.restDdMessageMockMvc = MockMvcBuilders.standaloneSetup(ddMessageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DdMessage createEntity(EntityManager em) {
        DdMessage ddMessage = new DdMessage()
            .receivingDepartment(DEFAULT_RECEIVING_DEPARTMENT)
            .receivingUser(DEFAULT_RECEIVING_USER)
            .title(DEFAULT_TITLE)
            .json(DEFAULT_JSON)
            .sendTime(DEFAULT_SEND_TIME)
            .type(DEFAULT_TYPE);
        return ddMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DdMessage createUpdatedEntity(EntityManager em) {
        DdMessage ddMessage = new DdMessage()
            .receivingDepartment(UPDATED_RECEIVING_DEPARTMENT)
            .receivingUser(UPDATED_RECEIVING_USER)
            .title(UPDATED_TITLE)
            .json(UPDATED_JSON)
            .sendTime(UPDATED_SEND_TIME)
            .type(UPDATED_TYPE);
        return ddMessage;
    }

    @BeforeEach
    public void initTest() {
        ddMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createDdMessage() throws Exception {
        int databaseSizeBeforeCreate = ddMessageRepository.findAll().size();

        // Create the DdMessage
        restDdMessageMockMvc.perform(post("/api/dd-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ddMessage)))
            .andExpect(status().isCreated());

        // Validate the DdMessage in the database
        List<DdMessage> ddMessageList = ddMessageRepository.findAll();
        assertThat(ddMessageList).hasSize(databaseSizeBeforeCreate + 1);
        DdMessage testDdMessage = ddMessageList.get(ddMessageList.size() - 1);
        assertThat(testDdMessage.getReceivingDepartment()).isEqualTo(DEFAULT_RECEIVING_DEPARTMENT);
        assertThat(testDdMessage.getReceivingUser()).isEqualTo(DEFAULT_RECEIVING_USER);
        assertThat(testDdMessage.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDdMessage.getJson()).isEqualTo(DEFAULT_JSON);
        assertThat(testDdMessage.getSendTime()).isEqualTo(DEFAULT_SEND_TIME);
        assertThat(testDdMessage.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createDdMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ddMessageRepository.findAll().size();

        // Create the DdMessage with an existing ID
        ddMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDdMessageMockMvc.perform(post("/api/dd-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ddMessage)))
            .andExpect(status().isBadRequest());

        // Validate the DdMessage in the database
        List<DdMessage> ddMessageList = ddMessageRepository.findAll();
        assertThat(ddMessageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDdMessages() throws Exception {
        // Initialize the database
        ddMessageRepository.saveAndFlush(ddMessage);

        // Get all the ddMessageList
        restDdMessageMockMvc.perform(get("/api/dd-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ddMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].receivingDepartment").value(hasItem(DEFAULT_RECEIVING_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].receivingUser").value(hasItem(DEFAULT_RECEIVING_USER)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].json").value(hasItem(DEFAULT_JSON)))
            .andExpect(jsonPath("$.[*].sendTime").value(hasItem(DEFAULT_SEND_TIME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDdMessage() throws Exception {
        // Initialize the database
        ddMessageRepository.saveAndFlush(ddMessage);

        // Get the ddMessage
        restDdMessageMockMvc.perform(get("/api/dd-messages/{id}", ddMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ddMessage.getId().intValue()))
            .andExpect(jsonPath("$.receivingDepartment").value(DEFAULT_RECEIVING_DEPARTMENT))
            .andExpect(jsonPath("$.receivingUser").value(DEFAULT_RECEIVING_USER))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.json").value(DEFAULT_JSON))
            .andExpect(jsonPath("$.sendTime").value(DEFAULT_SEND_TIME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDdMessage() throws Exception {
        // Get the ddMessage
        restDdMessageMockMvc.perform(get("/api/dd-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDdMessage() throws Exception {
        // Initialize the database
        ddMessageService.save(ddMessage);

        int databaseSizeBeforeUpdate = ddMessageRepository.findAll().size();

        // Update the ddMessage
        DdMessage updatedDdMessage = ddMessageRepository.findById(ddMessage.getId()).get();
        // Disconnect from session so that the updates on updatedDdMessage are not directly saved in db
        em.detach(updatedDdMessage);
        updatedDdMessage
            .receivingDepartment(UPDATED_RECEIVING_DEPARTMENT)
            .receivingUser(UPDATED_RECEIVING_USER)
            .title(UPDATED_TITLE)
            .json(UPDATED_JSON)
            .sendTime(UPDATED_SEND_TIME)
            .type(UPDATED_TYPE);

        restDdMessageMockMvc.perform(put("/api/dd-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDdMessage)))
            .andExpect(status().isOk());

        // Validate the DdMessage in the database
        List<DdMessage> ddMessageList = ddMessageRepository.findAll();
        assertThat(ddMessageList).hasSize(databaseSizeBeforeUpdate);
        DdMessage testDdMessage = ddMessageList.get(ddMessageList.size() - 1);
        assertThat(testDdMessage.getReceivingDepartment()).isEqualTo(UPDATED_RECEIVING_DEPARTMENT);
        assertThat(testDdMessage.getReceivingUser()).isEqualTo(UPDATED_RECEIVING_USER);
        assertThat(testDdMessage.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDdMessage.getJson()).isEqualTo(UPDATED_JSON);
        assertThat(testDdMessage.getSendTime()).isEqualTo(UPDATED_SEND_TIME);
        assertThat(testDdMessage.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDdMessage() throws Exception {
        int databaseSizeBeforeUpdate = ddMessageRepository.findAll().size();

        // Create the DdMessage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDdMessageMockMvc.perform(put("/api/dd-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ddMessage)))
            .andExpect(status().isBadRequest());

        // Validate the DdMessage in the database
        List<DdMessage> ddMessageList = ddMessageRepository.findAll();
        assertThat(ddMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDdMessage() throws Exception {
        // Initialize the database
        ddMessageService.save(ddMessage);

        int databaseSizeBeforeDelete = ddMessageRepository.findAll().size();

        // Delete the ddMessage
        restDdMessageMockMvc.perform(delete("/api/dd-messages/{id}", ddMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DdMessage> ddMessageList = ddMessageRepository.findAll();
        assertThat(ddMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
