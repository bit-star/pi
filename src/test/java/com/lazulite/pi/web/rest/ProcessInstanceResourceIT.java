package com.lazulite.pi.web.rest;

import com.lazulite.pi.PiApp;
import com.lazulite.pi.domain.ProcessInstance;
import com.lazulite.pi.repository.ProcessInstanceRepository;
import com.lazulite.pi.service.ProcessInstanceService;
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
 * Integration tests for the {@link ProcessInstanceResource} REST controller.
 */
@SpringBootTest(classes = PiApp.class)
public class ProcessInstanceResourceIT {

    private static final String DEFAULT_ATTACHED_PROCESS_INSTANCE_IDS = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHED_PROCESS_INSTANCE_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_BIZ_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_BIZ_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FINISH_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISH_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPERATION_RECORDS = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_RECORDS = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINATOR_DEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATOR_DEPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINATOR_DEPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATOR_DEPT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINATOR_USERID = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATOR_USERID = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TASKS = "AAAAAAAAAA";
    private static final String UPDATED_TASKS = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private ProcessInstanceService processInstanceService;

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

    private MockMvc restProcessInstanceMockMvc;

    private ProcessInstance processInstance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessInstanceResource processInstanceResource = new ProcessInstanceResource(processInstanceService);
        this.restProcessInstanceMockMvc = MockMvcBuilders.standaloneSetup(processInstanceResource)
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
    public static ProcessInstance createEntity(EntityManager em) {
        ProcessInstance processInstance = new ProcessInstance()
            .attachedProcessInstanceIds(DEFAULT_ATTACHED_PROCESS_INSTANCE_IDS)
            .bizAction(DEFAULT_BIZ_ACTION)
            .businessId(DEFAULT_BUSINESS_ID)
            .createTime(DEFAULT_CREATE_TIME)
            .finishTime(DEFAULT_FINISH_TIME)
            .operationRecords(DEFAULT_OPERATION_RECORDS)
            .originatorDeptId(DEFAULT_ORIGINATOR_DEPT_ID)
            .originatorDeptName(DEFAULT_ORIGINATOR_DEPT_NAME)
            .originatorUserid(DEFAULT_ORIGINATOR_USERID)
            .result(DEFAULT_RESULT)
            .status(DEFAULT_STATUS)
            .tasks(DEFAULT_TASKS)
            .title(DEFAULT_TITLE);
        return processInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessInstance createUpdatedEntity(EntityManager em) {
        ProcessInstance processInstance = new ProcessInstance()
            .attachedProcessInstanceIds(UPDATED_ATTACHED_PROCESS_INSTANCE_IDS)
            .bizAction(UPDATED_BIZ_ACTION)
            .businessId(UPDATED_BUSINESS_ID)
            .createTime(UPDATED_CREATE_TIME)
            .finishTime(UPDATED_FINISH_TIME)
            .operationRecords(UPDATED_OPERATION_RECORDS)
            .originatorDeptId(UPDATED_ORIGINATOR_DEPT_ID)
            .originatorDeptName(UPDATED_ORIGINATOR_DEPT_NAME)
            .originatorUserid(UPDATED_ORIGINATOR_USERID)
            .result(UPDATED_RESULT)
            .status(UPDATED_STATUS)
            .tasks(UPDATED_TASKS)
            .title(UPDATED_TITLE);
        return processInstance;
    }

    @BeforeEach
    public void initTest() {
        processInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessInstance() throws Exception {
        int databaseSizeBeforeCreate = processInstanceRepository.findAll().size();

        // Create the ProcessInstance
        restProcessInstanceMockMvc.perform(post("/api/process-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processInstance)))
            .andExpect(status().isCreated());

        // Validate the ProcessInstance in the database
        List<ProcessInstance> processInstanceList = processInstanceRepository.findAll();
        assertThat(processInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessInstance testProcessInstance = processInstanceList.get(processInstanceList.size() - 1);
        assertThat(testProcessInstance.getAttachedProcessInstanceIds()).isEqualTo(DEFAULT_ATTACHED_PROCESS_INSTANCE_IDS);
        assertThat(testProcessInstance.getBizAction()).isEqualTo(DEFAULT_BIZ_ACTION);
        assertThat(testProcessInstance.getBusinessId()).isEqualTo(DEFAULT_BUSINESS_ID);
        assertThat(testProcessInstance.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testProcessInstance.getFinishTime()).isEqualTo(DEFAULT_FINISH_TIME);
        assertThat(testProcessInstance.getOperationRecords()).isEqualTo(DEFAULT_OPERATION_RECORDS);
        assertThat(testProcessInstance.getOriginatorDeptId()).isEqualTo(DEFAULT_ORIGINATOR_DEPT_ID);
        assertThat(testProcessInstance.getOriginatorDeptName()).isEqualTo(DEFAULT_ORIGINATOR_DEPT_NAME);
        assertThat(testProcessInstance.getOriginatorUserid()).isEqualTo(DEFAULT_ORIGINATOR_USERID);
        assertThat(testProcessInstance.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testProcessInstance.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProcessInstance.getTasks()).isEqualTo(DEFAULT_TASKS);
        assertThat(testProcessInstance.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createProcessInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processInstanceRepository.findAll().size();

        // Create the ProcessInstance with an existing ID
        processInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessInstanceMockMvc.perform(post("/api/process-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processInstance)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessInstance in the database
        List<ProcessInstance> processInstanceList = processInstanceRepository.findAll();
        assertThat(processInstanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessInstances() throws Exception {
        // Initialize the database
        processInstanceRepository.saveAndFlush(processInstance);

        // Get all the processInstanceList
        restProcessInstanceMockMvc.perform(get("/api/process-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachedProcessInstanceIds").value(hasItem(DEFAULT_ATTACHED_PROCESS_INSTANCE_IDS)))
            .andExpect(jsonPath("$.[*].bizAction").value(hasItem(DEFAULT_BIZ_ACTION)))
            .andExpect(jsonPath("$.[*].businessId").value(hasItem(DEFAULT_BUSINESS_ID)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].finishTime").value(hasItem(DEFAULT_FINISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].operationRecords").value(hasItem(DEFAULT_OPERATION_RECORDS)))
            .andExpect(jsonPath("$.[*].originatorDeptId").value(hasItem(DEFAULT_ORIGINATOR_DEPT_ID)))
            .andExpect(jsonPath("$.[*].originatorDeptName").value(hasItem(DEFAULT_ORIGINATOR_DEPT_NAME)))
            .andExpect(jsonPath("$.[*].originatorUserid").value(hasItem(DEFAULT_ORIGINATOR_USERID)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].tasks").value(hasItem(DEFAULT_TASKS)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getProcessInstance() throws Exception {
        // Initialize the database
        processInstanceRepository.saveAndFlush(processInstance);

        // Get the processInstance
        restProcessInstanceMockMvc.perform(get("/api/process-instances/{id}", processInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processInstance.getId().intValue()))
            .andExpect(jsonPath("$.attachedProcessInstanceIds").value(DEFAULT_ATTACHED_PROCESS_INSTANCE_IDS))
            .andExpect(jsonPath("$.bizAction").value(DEFAULT_BIZ_ACTION))
            .andExpect(jsonPath("$.businessId").value(DEFAULT_BUSINESS_ID))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.finishTime").value(DEFAULT_FINISH_TIME.toString()))
            .andExpect(jsonPath("$.operationRecords").value(DEFAULT_OPERATION_RECORDS))
            .andExpect(jsonPath("$.originatorDeptId").value(DEFAULT_ORIGINATOR_DEPT_ID))
            .andExpect(jsonPath("$.originatorDeptName").value(DEFAULT_ORIGINATOR_DEPT_NAME))
            .andExpect(jsonPath("$.originatorUserid").value(DEFAULT_ORIGINATOR_USERID))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.tasks").value(DEFAULT_TASKS))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    public void getNonExistingProcessInstance() throws Exception {
        // Get the processInstance
        restProcessInstanceMockMvc.perform(get("/api/process-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessInstance() throws Exception {
        // Initialize the database
        processInstanceService.save(processInstance);

        int databaseSizeBeforeUpdate = processInstanceRepository.findAll().size();

        // Update the processInstance
        ProcessInstance updatedProcessInstance = processInstanceRepository.findById(processInstance.getId()).get();
        // Disconnect from session so that the updates on updatedProcessInstance are not directly saved in db
        em.detach(updatedProcessInstance);
        updatedProcessInstance
            .attachedProcessInstanceIds(UPDATED_ATTACHED_PROCESS_INSTANCE_IDS)
            .bizAction(UPDATED_BIZ_ACTION)
            .businessId(UPDATED_BUSINESS_ID)
            .createTime(UPDATED_CREATE_TIME)
            .finishTime(UPDATED_FINISH_TIME)
            .operationRecords(UPDATED_OPERATION_RECORDS)
            .originatorDeptId(UPDATED_ORIGINATOR_DEPT_ID)
            .originatorDeptName(UPDATED_ORIGINATOR_DEPT_NAME)
            .originatorUserid(UPDATED_ORIGINATOR_USERID)
            .result(UPDATED_RESULT)
            .status(UPDATED_STATUS)
            .tasks(UPDATED_TASKS)
            .title(UPDATED_TITLE);

        restProcessInstanceMockMvc.perform(put("/api/process-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessInstance)))
            .andExpect(status().isOk());

        // Validate the ProcessInstance in the database
        List<ProcessInstance> processInstanceList = processInstanceRepository.findAll();
        assertThat(processInstanceList).hasSize(databaseSizeBeforeUpdate);
        ProcessInstance testProcessInstance = processInstanceList.get(processInstanceList.size() - 1);
        assertThat(testProcessInstance.getAttachedProcessInstanceIds()).isEqualTo(UPDATED_ATTACHED_PROCESS_INSTANCE_IDS);
        assertThat(testProcessInstance.getBizAction()).isEqualTo(UPDATED_BIZ_ACTION);
        assertThat(testProcessInstance.getBusinessId()).isEqualTo(UPDATED_BUSINESS_ID);
        assertThat(testProcessInstance.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testProcessInstance.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testProcessInstance.getOperationRecords()).isEqualTo(UPDATED_OPERATION_RECORDS);
        assertThat(testProcessInstance.getOriginatorDeptId()).isEqualTo(UPDATED_ORIGINATOR_DEPT_ID);
        assertThat(testProcessInstance.getOriginatorDeptName()).isEqualTo(UPDATED_ORIGINATOR_DEPT_NAME);
        assertThat(testProcessInstance.getOriginatorUserid()).isEqualTo(UPDATED_ORIGINATOR_USERID);
        assertThat(testProcessInstance.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testProcessInstance.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProcessInstance.getTasks()).isEqualTo(UPDATED_TASKS);
        assertThat(testProcessInstance.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessInstance() throws Exception {
        int databaseSizeBeforeUpdate = processInstanceRepository.findAll().size();

        // Create the ProcessInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessInstanceMockMvc.perform(put("/api/process-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processInstance)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessInstance in the database
        List<ProcessInstance> processInstanceList = processInstanceRepository.findAll();
        assertThat(processInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessInstance() throws Exception {
        // Initialize the database
        processInstanceService.save(processInstance);

        int databaseSizeBeforeDelete = processInstanceRepository.findAll().size();

        // Delete the processInstance
        restProcessInstanceMockMvc.perform(delete("/api/process-instances/{id}", processInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessInstance> processInstanceList = processInstanceRepository.findAll();
        assertThat(processInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
