package com.lazulite.pi.service;

import com.lazulite.pi.domain.FormComponentValues;
import com.lazulite.pi.repository.FormComponentValuesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FormComponentValues}.
 */
@Service
@Transactional
public class FormComponentValuesService {

    private final Logger log = LoggerFactory.getLogger(FormComponentValuesService.class);

    private final FormComponentValuesRepository formComponentValuesRepository;

    public FormComponentValuesService(FormComponentValuesRepository formComponentValuesRepository) {
        this.formComponentValuesRepository = formComponentValuesRepository;
    }

    /**
     * Save a formComponentValues.
     *
     * @param formComponentValues the entity to save.
     * @return the persisted entity.
     */
    public FormComponentValues save(FormComponentValues formComponentValues) {
        log.debug("Request to save FormComponentValues : {}", formComponentValues);
        return formComponentValuesRepository.save(formComponentValues);
    }

    /**
     * Get all the formComponentValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FormComponentValues> findAll(Pageable pageable) {
        log.debug("Request to get all FormComponentValues");
        return formComponentValuesRepository.findAll(pageable);
    }


    /**
     * Get one formComponentValues by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FormComponentValues> findOne(Long id) {
        log.debug("Request to get FormComponentValues : {}", id);
        return formComponentValuesRepository.findById(id);
    }

    /**
     * Delete the formComponentValues by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FormComponentValues : {}", id);
        formComponentValuesRepository.deleteById(id);
    }
}
