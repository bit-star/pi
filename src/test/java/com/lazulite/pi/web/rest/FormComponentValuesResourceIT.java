package com.lazulite.pi.web.rest;

import com.lazulite.pi.PiApp;
import com.lazulite.pi.domain.FormComponentValues;
import com.lazulite.pi.repository.FormComponentValuesRepository;
import com.lazulite.pi.service.FormComponentValuesService;
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
 * Integration tests for the {@link FormComponentValuesResource} REST controller.
 */
@SpringBootTest(classes = PiApp.class)
public class FormComponentValuesResourceIT {

    private static final String DEFAULT_COMPONENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_EXT_VALUE = "BBBBBBBBBB";

    @Autowired
    private FormComponentValuesRepository formComponentValuesRepository;

    @Autowired
    private FormComponentValuesService formComponentValuesService;

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

    private MockMvc restFormComponentValuesMockMvc;

    private FormComponentValues formComponentValues;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormComponentValuesResource formComponentValuesResource = new FormComponentValuesResource(formComponentValuesService);
        this.restFormComponentValuesMockMvc = MockMvcBuilders.standaloneSetup(formComponentValuesResource)
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
    public static FormComponentValues createEntity(EntityManager em) {
        FormComponentValues formComponentValues = new FormComponentValues()
            .componentType(DEFAULT_COMPONENT_TYPE)
            .value(DEFAULT_VALUE)
            .name(DEFAULT_NAME)
            .extValue(DEFAULT_EXT_VALUE);
        return formComponentValues;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormComponentValues createUpdatedEntity(EntityManager em) {
        FormComponentValues formComponentValues = new FormComponentValues()
            .componentType(UPDATED_COMPONENT_TYPE)
            .value(UPDATED_VALUE)
            .name(UPDATED_NAME)
            .extValue(UPDATED_EXT_VALUE);
        return formComponentValues;
    }

    @BeforeEach
    public void initTest() {
        formComponentValues = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormComponentValues() throws Exception {
        int databaseSizeBeforeCreate = formComponentValuesRepository.findAll().size();

        // Create the FormComponentValues
        restFormComponentValuesMockMvc.perform(post("/api/form-component-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formComponentValues)))
            .andExpect(status().isCreated());

        // Validate the FormComponentValues in the database
        List<FormComponentValues> formComponentValuesList = formComponentValuesRepository.findAll();
        assertThat(formComponentValuesList).hasSize(databaseSizeBeforeCreate + 1);
        FormComponentValues testFormComponentValues = formComponentValuesList.get(formComponentValuesList.size() - 1);
        assertThat(testFormComponentValues.getComponentType()).isEqualTo(DEFAULT_COMPONENT_TYPE);
        assertThat(testFormComponentValues.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testFormComponentValues.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormComponentValues.getExtValue()).isEqualTo(DEFAULT_EXT_VALUE);
    }

    @Test
    @Transactional
    public void createFormComponentValuesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formComponentValuesRepository.findAll().size();

        // Create the FormComponentValues with an existing ID
        formComponentValues.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormComponentValuesMockMvc.perform(post("/api/form-component-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formComponentValues)))
            .andExpect(status().isBadRequest());

        // Validate the FormComponentValues in the database
        List<FormComponentValues> formComponentValuesList = formComponentValuesRepository.findAll();
        assertThat(formComponentValuesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFormComponentValues() throws Exception {
        // Initialize the database
        formComponentValuesRepository.saveAndFlush(formComponentValues);

        // Get all the formComponentValuesList
        restFormComponentValuesMockMvc.perform(get("/api/form-component-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formComponentValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].componentType").value(hasItem(DEFAULT_COMPONENT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].extValue").value(hasItem(DEFAULT_EXT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getFormComponentValues() throws Exception {
        // Initialize the database
        formComponentValuesRepository.saveAndFlush(formComponentValues);

        // Get the formComponentValues
        restFormComponentValuesMockMvc.perform(get("/api/form-component-values/{id}", formComponentValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formComponentValues.getId().intValue()))
            .andExpect(jsonPath("$.componentType").value(DEFAULT_COMPONENT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.extValue").value(DEFAULT_EXT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingFormComponentValues() throws Exception {
        // Get the formComponentValues
        restFormComponentValuesMockMvc.perform(get("/api/form-component-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormComponentValues() throws Exception {
        // Initialize the database
        formComponentValuesService.save(formComponentValues);

        int databaseSizeBeforeUpdate = formComponentValuesRepository.findAll().size();

        // Update the formComponentValues
        FormComponentValues updatedFormComponentValues = formComponentValuesRepository.findById(formComponentValues.getId()).get();
        // Disconnect from session so that the updates on updatedFormComponentValues are not directly saved in db
        em.detach(updatedFormComponentValues);
        updatedFormComponentValues
            .componentType(UPDATED_COMPONENT_TYPE)
            .value(UPDATED_VALUE)
            .name(UPDATED_NAME)
            .extValue(UPDATED_EXT_VALUE);

        restFormComponentValuesMockMvc.perform(put("/api/form-component-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormComponentValues)))
            .andExpect(status().isOk());

        // Validate the FormComponentValues in the database
        List<FormComponentValues> formComponentValuesList = formComponentValuesRepository.findAll();
        assertThat(formComponentValuesList).hasSize(databaseSizeBeforeUpdate);
        FormComponentValues testFormComponentValues = formComponentValuesList.get(formComponentValuesList.size() - 1);
        assertThat(testFormComponentValues.getComponentType()).isEqualTo(UPDATED_COMPONENT_TYPE);
        assertThat(testFormComponentValues.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFormComponentValues.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormComponentValues.getExtValue()).isEqualTo(UPDATED_EXT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingFormComponentValues() throws Exception {
        int databaseSizeBeforeUpdate = formComponentValuesRepository.findAll().size();

        // Create the FormComponentValues

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormComponentValuesMockMvc.perform(put("/api/form-component-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formComponentValues)))
            .andExpect(status().isBadRequest());

        // Validate the FormComponentValues in the database
        List<FormComponentValues> formComponentValuesList = formComponentValuesRepository.findAll();
        assertThat(formComponentValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormComponentValues() throws Exception {
        // Initialize the database
        formComponentValuesService.save(formComponentValues);

        int databaseSizeBeforeDelete = formComponentValuesRepository.findAll().size();

        // Delete the formComponentValues
        restFormComponentValuesMockMvc.perform(delete("/api/form-component-values/{id}", formComponentValues.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormComponentValues> formComponentValuesList = formComponentValuesRepository.findAll();
        assertThat(formComponentValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
