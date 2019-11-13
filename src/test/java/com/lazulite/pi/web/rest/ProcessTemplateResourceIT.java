package com.lazulite.pi.web.rest;

import com.lazulite.pi.PiApp;
import com.lazulite.pi.domain.ProcessTemplate;
import com.lazulite.pi.repository.ProcessTemplateRepository;
import com.lazulite.pi.service.ProcessTemplateService;
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
import java.util.List;

import static com.lazulite.pi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProcessTemplateResource} REST controller.
 */
@SpringBootTest(classes = PiApp.class)
public class ProcessTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_CODE = "BBBBBBBBBB";

    @Autowired
    private ProcessTemplateRepository processTemplateRepository;

    @Autowired
    private ProcessTemplateService processTemplateService;

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

    private MockMvc restProcessTemplateMockMvc;

    private ProcessTemplate processTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessTemplateResource processTemplateResource = new ProcessTemplateResource(processTemplateService);
        this.restProcessTemplateMockMvc = MockMvcBuilders.standaloneSetup(processTemplateResource)
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
    public static ProcessTemplate createEntity(EntityManager em) {
        ProcessTemplate processTemplate = new ProcessTemplate()
            .name(DEFAULT_NAME)
            .processCode(DEFAULT_PROCESS_CODE);
        return processTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessTemplate createUpdatedEntity(EntityManager em) {
        ProcessTemplate processTemplate = new ProcessTemplate()
            .name(UPDATED_NAME)
            .processCode(UPDATED_PROCESS_CODE);
        return processTemplate;
    }

    @BeforeEach
    public void initTest() {
        processTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessTemplate() throws Exception {
        int databaseSizeBeforeCreate = processTemplateRepository.findAll().size();

        // Create the ProcessTemplate
        restProcessTemplateMockMvc.perform(post("/api/process-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processTemplate)))
            .andExpect(status().isCreated());

        // Validate the ProcessTemplate in the database
        List<ProcessTemplate> processTemplateList = processTemplateRepository.findAll();
        assertThat(processTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessTemplate testProcessTemplate = processTemplateList.get(processTemplateList.size() - 1);
        assertThat(testProcessTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProcessTemplate.getProcessCode()).isEqualTo(DEFAULT_PROCESS_CODE);
    }

    @Test
    @Transactional
    public void createProcessTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processTemplateRepository.findAll().size();

        // Create the ProcessTemplate with an existing ID
        processTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessTemplateMockMvc.perform(post("/api/process-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessTemplate in the database
        List<ProcessTemplate> processTemplateList = processTemplateRepository.findAll();
        assertThat(processTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessTemplates() throws Exception {
        // Initialize the database
        processTemplateRepository.saveAndFlush(processTemplate);

        // Get all the processTemplateList
        restProcessTemplateMockMvc.perform(get("/api/process-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].processCode").value(hasItem(DEFAULT_PROCESS_CODE)));
    }
    
    @Test
    @Transactional
    public void getProcessTemplate() throws Exception {
        // Initialize the database
        processTemplateRepository.saveAndFlush(processTemplate);

        // Get the processTemplate
        restProcessTemplateMockMvc.perform(get("/api/process-templates/{id}", processTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.processCode").value(DEFAULT_PROCESS_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingProcessTemplate() throws Exception {
        // Get the processTemplate
        restProcessTemplateMockMvc.perform(get("/api/process-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessTemplate() throws Exception {
        // Initialize the database
        processTemplateService.save(processTemplate);

        int databaseSizeBeforeUpdate = processTemplateRepository.findAll().size();

        // Update the processTemplate
        ProcessTemplate updatedProcessTemplate = processTemplateRepository.findById(processTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedProcessTemplate are not directly saved in db
        em.detach(updatedProcessTemplate);
        updatedProcessTemplate
            .name(UPDATED_NAME)
            .processCode(UPDATED_PROCESS_CODE);

        restProcessTemplateMockMvc.perform(put("/api/process-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessTemplate)))
            .andExpect(status().isOk());

        // Validate the ProcessTemplate in the database
        List<ProcessTemplate> processTemplateList = processTemplateRepository.findAll();
        assertThat(processTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProcessTemplate testProcessTemplate = processTemplateList.get(processTemplateList.size() - 1);
        assertThat(testProcessTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcessTemplate.getProcessCode()).isEqualTo(UPDATED_PROCESS_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessTemplate() throws Exception {
        int databaseSizeBeforeUpdate = processTemplateRepository.findAll().size();

        // Create the ProcessTemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessTemplateMockMvc.perform(put("/api/process-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessTemplate in the database
        List<ProcessTemplate> processTemplateList = processTemplateRepository.findAll();
        assertThat(processTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessTemplate() throws Exception {
        // Initialize the database
        processTemplateService.save(processTemplate);

        int databaseSizeBeforeDelete = processTemplateRepository.findAll().size();

        // Delete the processTemplate
        restProcessTemplateMockMvc.perform(delete("/api/process-templates/{id}", processTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessTemplate> processTemplateList = processTemplateRepository.findAll();
        assertThat(processTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
