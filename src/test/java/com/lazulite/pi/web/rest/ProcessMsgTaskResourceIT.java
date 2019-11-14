package com.lazulite.pi.web.rest;

import com.lazulite.pi.PiApp;
import com.lazulite.pi.domain.ProcessMsgTask;
import com.lazulite.pi.repository.ProcessMsgTaskRepository;
import com.lazulite.pi.service.ProcessMsgTaskService;
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

import com.lazulite.pi.domain.enumeration.MessageStatus;
/**
 * Integration tests for the {@link ProcessMsgTaskResource} REST controller.
 */
@SpringBootTest(classes = PiApp.class)
public class ProcessMsgTaskResourceIT {

    private static final String DEFAULT_RECEIVING_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVING_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVING_USER = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVING_USER = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_JSON = "AAAAAAAAAA";
    private static final String UPDATED_JSON = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXECUTE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final MessageStatus DEFAULT_STATUS = MessageStatus.SentSuccessfully;
    private static final MessageStatus UPDATED_STATUS = MessageStatus.NotSentYet;

    @Autowired
    private ProcessMsgTaskRepository processMsgTaskRepository;

    @Autowired
    private ProcessMsgTaskService processMsgTaskService;

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

    private MockMvc restProcessMsgTaskMockMvc;

    private ProcessMsgTask processMsgTask;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessMsgTaskResource processMsgTaskResource = new ProcessMsgTaskResource(processMsgTaskService);
        this.restProcessMsgTaskMockMvc = MockMvcBuilders.standaloneSetup(processMsgTaskResource)
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
    public static ProcessMsgTask createEntity(EntityManager em) {
        ProcessMsgTask processMsgTask = new ProcessMsgTask()
            .receivingDepartment(DEFAULT_RECEIVING_DEPARTMENT)
            .receivingUser(DEFAULT_RECEIVING_USER)
            .title(DEFAULT_TITLE)
            .json(DEFAULT_JSON)
            .executeTime(DEFAULT_EXECUTE_TIME)
            .status(DEFAULT_STATUS);
        return processMsgTask;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessMsgTask createUpdatedEntity(EntityManager em) {
        ProcessMsgTask processMsgTask = new ProcessMsgTask()
            .receivingDepartment(UPDATED_RECEIVING_DEPARTMENT)
            .receivingUser(UPDATED_RECEIVING_USER)
            .title(UPDATED_TITLE)
            .json(UPDATED_JSON)
            .executeTime(UPDATED_EXECUTE_TIME)
            .status(UPDATED_STATUS);
        return processMsgTask;
    }

    @BeforeEach
    public void initTest() {
        processMsgTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessMsgTask() throws Exception {
        int databaseSizeBeforeCreate = processMsgTaskRepository.findAll().size();

        // Create the ProcessMsgTask
        restProcessMsgTaskMockMvc.perform(post("/api/process-msg-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processMsgTask)))
            .andExpect(status().isCreated());

        // Validate the ProcessMsgTask in the database
        List<ProcessMsgTask> processMsgTaskList = processMsgTaskRepository.findAll();
        assertThat(processMsgTaskList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessMsgTask testProcessMsgTask = processMsgTaskList.get(processMsgTaskList.size() - 1);
        assertThat(testProcessMsgTask.getReceivingDepartment()).isEqualTo(DEFAULT_RECEIVING_DEPARTMENT);
        assertThat(testProcessMsgTask.getReceivingUser()).isEqualTo(DEFAULT_RECEIVING_USER);
        assertThat(testProcessMsgTask.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcessMsgTask.getJson()).isEqualTo(DEFAULT_JSON);
        assertThat(testProcessMsgTask.getExecuteTime()).isEqualTo(DEFAULT_EXECUTE_TIME);
        assertThat(testProcessMsgTask.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createProcessMsgTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processMsgTaskRepository.findAll().size();

        // Create the ProcessMsgTask with an existing ID
        processMsgTask.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessMsgTaskMockMvc.perform(post("/api/process-msg-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processMsgTask)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessMsgTask in the database
        List<ProcessMsgTask> processMsgTaskList = processMsgTaskRepository.findAll();
        assertThat(processMsgTaskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessMsgTasks() throws Exception {
        // Initialize the database
        processMsgTaskRepository.saveAndFlush(processMsgTask);

        // Get all the processMsgTaskList
        restProcessMsgTaskMockMvc.perform(get("/api/process-msg-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processMsgTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].receivingDepartment").value(hasItem(DEFAULT_RECEIVING_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].receivingUser").value(hasItem(DEFAULT_RECEIVING_USER)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].json").value(hasItem(DEFAULT_JSON)))
            .andExpect(jsonPath("$.[*].executeTime").value(hasItem(DEFAULT_EXECUTE_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getProcessMsgTask() throws Exception {
        // Initialize the database
        processMsgTaskRepository.saveAndFlush(processMsgTask);

        // Get the processMsgTask
        restProcessMsgTaskMockMvc.perform(get("/api/process-msg-tasks/{id}", processMsgTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processMsgTask.getId().intValue()))
            .andExpect(jsonPath("$.receivingDepartment").value(DEFAULT_RECEIVING_DEPARTMENT))
            .andExpect(jsonPath("$.receivingUser").value(DEFAULT_RECEIVING_USER))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.json").value(DEFAULT_JSON))
            .andExpect(jsonPath("$.executeTime").value(DEFAULT_EXECUTE_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcessMsgTask() throws Exception {
        // Get the processMsgTask
        restProcessMsgTaskMockMvc.perform(get("/api/process-msg-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessMsgTask() throws Exception {
        // Initialize the database
        processMsgTaskService.save(processMsgTask);

        int databaseSizeBeforeUpdate = processMsgTaskRepository.findAll().size();

        // Update the processMsgTask
        ProcessMsgTask updatedProcessMsgTask = processMsgTaskRepository.findById(processMsgTask.getId()).get();
        // Disconnect from session so that the updates on updatedProcessMsgTask are not directly saved in db
        em.detach(updatedProcessMsgTask);
        updatedProcessMsgTask
            .receivingDepartment(UPDATED_RECEIVING_DEPARTMENT)
            .receivingUser(UPDATED_RECEIVING_USER)
            .title(UPDATED_TITLE)
            .json(UPDATED_JSON)
            .executeTime(UPDATED_EXECUTE_TIME)
            .status(UPDATED_STATUS);

        restProcessMsgTaskMockMvc.perform(put("/api/process-msg-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessMsgTask)))
            .andExpect(status().isOk());

        // Validate the ProcessMsgTask in the database
        List<ProcessMsgTask> processMsgTaskList = processMsgTaskRepository.findAll();
        assertThat(processMsgTaskList).hasSize(databaseSizeBeforeUpdate);
        ProcessMsgTask testProcessMsgTask = processMsgTaskList.get(processMsgTaskList.size() - 1);
        assertThat(testProcessMsgTask.getReceivingDepartment()).isEqualTo(UPDATED_RECEIVING_DEPARTMENT);
        assertThat(testProcessMsgTask.getReceivingUser()).isEqualTo(UPDATED_RECEIVING_USER);
        assertThat(testProcessMsgTask.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcessMsgTask.getJson()).isEqualTo(UPDATED_JSON);
        assertThat(testProcessMsgTask.getExecuteTime()).isEqualTo(UPDATED_EXECUTE_TIME);
        assertThat(testProcessMsgTask.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessMsgTask() throws Exception {
        int databaseSizeBeforeUpdate = processMsgTaskRepository.findAll().size();

        // Create the ProcessMsgTask

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessMsgTaskMockMvc.perform(put("/api/process-msg-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processMsgTask)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessMsgTask in the database
        List<ProcessMsgTask> processMsgTaskList = processMsgTaskRepository.findAll();
        assertThat(processMsgTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessMsgTask() throws Exception {
        // Initialize the database
        processMsgTaskService.save(processMsgTask);

        int databaseSizeBeforeDelete = processMsgTaskRepository.findAll().size();

        // Delete the processMsgTask
        restProcessMsgTaskMockMvc.perform(delete("/api/process-msg-tasks/{id}", processMsgTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessMsgTask> processMsgTaskList = processMsgTaskRepository.findAll();
        assertThat(processMsgTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
