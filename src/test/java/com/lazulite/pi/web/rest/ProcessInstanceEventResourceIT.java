package com.lazulite.pi.web.rest;

import com.lazulite.pi.PiApp;
import com.lazulite.pi.domain.ProcessInstanceEvent;
import com.lazulite.pi.repository.ProcessInstanceEventRepository;
import com.lazulite.pi.service.ProcessInstanceEventService;
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

/**
 * Integration tests for the {@link ProcessInstanceEventResource} REST controller.
 */
@SpringBootTest(classes = PiApp.class)
public class ProcessInstanceEventResourceIT {

    private static final Instant DEFAULT_FINISH_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISH_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CORP_ID = "AAAAAAAAAA";
    private static final String UPDATED_CORP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BIZ_CATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_BIZ_CATEGORY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STAFF_ID = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_ID = "BBBBBBBBBB";

    @Autowired
    private ProcessInstanceEventRepository processInstanceEventRepository;

    @Autowired
    private ProcessInstanceEventService processInstanceEventService;

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

    private MockMvc restProcessInstanceEventMockMvc;

    private ProcessInstanceEvent processInstanceEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessInstanceEventResource processInstanceEventResource = new ProcessInstanceEventResource(processInstanceEventService);
        this.restProcessInstanceEventMockMvc = MockMvcBuilders.standaloneSetup(processInstanceEventResource)
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
    public static ProcessInstanceEvent createEntity(EntityManager em) {
        ProcessInstanceEvent processInstanceEvent = new ProcessInstanceEvent()
            .finishTime(DEFAULT_FINISH_TIME)
            .corpId(DEFAULT_CORP_ID)
            .eventType(DEFAULT_EVENT_TYPE)
            .businessId(DEFAULT_BUSINESS_ID)
            .title(DEFAULT_TITLE)
            .type(DEFAULT_TYPE)
            .url(DEFAULT_URL)
            .result(DEFAULT_RESULT)
            .createTime(DEFAULT_CREATE_TIME)
            .processCode(DEFAULT_PROCESS_CODE)
            .bizCategoryId(DEFAULT_BIZ_CATEGORY_ID)
            .staffId(DEFAULT_STAFF_ID);
        return processInstanceEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessInstanceEvent createUpdatedEntity(EntityManager em) {
        ProcessInstanceEvent processInstanceEvent = new ProcessInstanceEvent()
            .finishTime(UPDATED_FINISH_TIME)
            .corpId(UPDATED_CORP_ID)
            .eventType(UPDATED_EVENT_TYPE)
            .businessId(UPDATED_BUSINESS_ID)
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .url(UPDATED_URL)
            .result(UPDATED_RESULT)
            .createTime(UPDATED_CREATE_TIME)
            .processCode(UPDATED_PROCESS_CODE)
            .bizCategoryId(UPDATED_BIZ_CATEGORY_ID)
            .staffId(UPDATED_STAFF_ID);
        return processInstanceEvent;
    }

    @BeforeEach
    public void initTest() {
        processInstanceEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessInstanceEvent() throws Exception {
        int databaseSizeBeforeCreate = processInstanceEventRepository.findAll().size();

        // Create the ProcessInstanceEvent
        restProcessInstanceEventMockMvc.perform(post("/api/process-instance-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processInstanceEvent)))
            .andExpect(status().isCreated());

        // Validate the ProcessInstanceEvent in the database
        List<ProcessInstanceEvent> processInstanceEventList = processInstanceEventRepository.findAll();
        assertThat(processInstanceEventList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessInstanceEvent testProcessInstanceEvent = processInstanceEventList.get(processInstanceEventList.size() - 1);
        assertThat(testProcessInstanceEvent.getFinishTime()).isEqualTo(DEFAULT_FINISH_TIME);
        assertThat(testProcessInstanceEvent.getCorpId()).isEqualTo(DEFAULT_CORP_ID);
        assertThat(testProcessInstanceEvent.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testProcessInstanceEvent.getBusinessId()).isEqualTo(DEFAULT_BUSINESS_ID);
        assertThat(testProcessInstanceEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcessInstanceEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProcessInstanceEvent.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testProcessInstanceEvent.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testProcessInstanceEvent.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testProcessInstanceEvent.getProcessCode()).isEqualTo(DEFAULT_PROCESS_CODE);
        assertThat(testProcessInstanceEvent.getBizCategoryId()).isEqualTo(DEFAULT_BIZ_CATEGORY_ID);
        assertThat(testProcessInstanceEvent.getStaffId()).isEqualTo(DEFAULT_STAFF_ID);
    }

    @Test
    @Transactional
    public void createProcessInstanceEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processInstanceEventRepository.findAll().size();

        // Create the ProcessInstanceEvent with an existing ID
        processInstanceEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessInstanceEventMockMvc.perform(post("/api/process-instance-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processInstanceEvent)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessInstanceEvent in the database
        List<ProcessInstanceEvent> processInstanceEventList = processInstanceEventRepository.findAll();
        assertThat(processInstanceEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessInstanceEvents() throws Exception {
        // Initialize the database
        processInstanceEventRepository.saveAndFlush(processInstanceEvent);

        // Get all the processInstanceEventList
        restProcessInstanceEventMockMvc.perform(get("/api/process-instance-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processInstanceEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].finishTime").value(hasItem(DEFAULT_FINISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].corpId").value(hasItem(DEFAULT_CORP_ID)))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].businessId").value(hasItem(DEFAULT_BUSINESS_ID)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.[*].processCode").value(hasItem(DEFAULT_PROCESS_CODE)))
            .andExpect(jsonPath("$.[*].bizCategoryId").value(hasItem(DEFAULT_BIZ_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].staffId").value(hasItem(DEFAULT_STAFF_ID)));
    }
    
    @Test
    @Transactional
    public void getProcessInstanceEvent() throws Exception {
        // Initialize the database
        processInstanceEventRepository.saveAndFlush(processInstanceEvent);

        // Get the processInstanceEvent
        restProcessInstanceEventMockMvc.perform(get("/api/process-instance-events/{id}", processInstanceEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processInstanceEvent.getId().intValue()))
            .andExpect(jsonPath("$.finishTime").value(DEFAULT_FINISH_TIME.toString()))
            .andExpect(jsonPath("$.corpId").value(DEFAULT_CORP_ID))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.businessId").value(DEFAULT_BUSINESS_ID))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME))
            .andExpect(jsonPath("$.processCode").value(DEFAULT_PROCESS_CODE))
            .andExpect(jsonPath("$.bizCategoryId").value(DEFAULT_BIZ_CATEGORY_ID))
            .andExpect(jsonPath("$.staffId").value(DEFAULT_STAFF_ID));
    }

    @Test
    @Transactional
    public void getNonExistingProcessInstanceEvent() throws Exception {
        // Get the processInstanceEvent
        restProcessInstanceEventMockMvc.perform(get("/api/process-instance-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessInstanceEvent() throws Exception {
        // Initialize the database
        processInstanceEventService.save(processInstanceEvent);

        int databaseSizeBeforeUpdate = processInstanceEventRepository.findAll().size();

        // Update the processInstanceEvent
        ProcessInstanceEvent updatedProcessInstanceEvent = processInstanceEventRepository.findById(processInstanceEvent.getId()).get();
        // Disconnect from session so that the updates on updatedProcessInstanceEvent are not directly saved in db
        em.detach(updatedProcessInstanceEvent);
        updatedProcessInstanceEvent
            .finishTime(UPDATED_FINISH_TIME)
            .corpId(UPDATED_CORP_ID)
            .eventType(UPDATED_EVENT_TYPE)
            .businessId(UPDATED_BUSINESS_ID)
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .url(UPDATED_URL)
            .result(UPDATED_RESULT)
            .createTime(UPDATED_CREATE_TIME)
            .processCode(UPDATED_PROCESS_CODE)
            .bizCategoryId(UPDATED_BIZ_CATEGORY_ID)
            .staffId(UPDATED_STAFF_ID);

        restProcessInstanceEventMockMvc.perform(put("/api/process-instance-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessInstanceEvent)))
            .andExpect(status().isOk());

        // Validate the ProcessInstanceEvent in the database
        List<ProcessInstanceEvent> processInstanceEventList = processInstanceEventRepository.findAll();
        assertThat(processInstanceEventList).hasSize(databaseSizeBeforeUpdate);
        ProcessInstanceEvent testProcessInstanceEvent = processInstanceEventList.get(processInstanceEventList.size() - 1);
        assertThat(testProcessInstanceEvent.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testProcessInstanceEvent.getCorpId()).isEqualTo(UPDATED_CORP_ID);
        assertThat(testProcessInstanceEvent.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testProcessInstanceEvent.getBusinessId()).isEqualTo(UPDATED_BUSINESS_ID);
        assertThat(testProcessInstanceEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcessInstanceEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProcessInstanceEvent.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testProcessInstanceEvent.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testProcessInstanceEvent.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testProcessInstanceEvent.getProcessCode()).isEqualTo(UPDATED_PROCESS_CODE);
        assertThat(testProcessInstanceEvent.getBizCategoryId()).isEqualTo(UPDATED_BIZ_CATEGORY_ID);
        assertThat(testProcessInstanceEvent.getStaffId()).isEqualTo(UPDATED_STAFF_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessInstanceEvent() throws Exception {
        int databaseSizeBeforeUpdate = processInstanceEventRepository.findAll().size();

        // Create the ProcessInstanceEvent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessInstanceEventMockMvc.perform(put("/api/process-instance-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processInstanceEvent)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessInstanceEvent in the database
        List<ProcessInstanceEvent> processInstanceEventList = processInstanceEventRepository.findAll();
        assertThat(processInstanceEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessInstanceEvent() throws Exception {
        // Initialize the database
        processInstanceEventService.save(processInstanceEvent);

        int databaseSizeBeforeDelete = processInstanceEventRepository.findAll().size();

        // Delete the processInstanceEvent
        restProcessInstanceEventMockMvc.perform(delete("/api/process-instance-events/{id}", processInstanceEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessInstanceEvent> processInstanceEventList = processInstanceEventRepository.findAll();
        assertThat(processInstanceEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
