package com.lazulite.pi.service;

import com.lazulite.pi.domain.ProcessTemplate;
import com.lazulite.pi.repository.ProcessTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessTemplate}.
 */
@Service
@Transactional
public class ProcessTemplateService {

    private final Logger log = LoggerFactory.getLogger(ProcessTemplateService.class);

    private final ProcessTemplateRepository processTemplateRepository;

    public ProcessTemplateService(ProcessTemplateRepository processTemplateRepository) {
        this.processTemplateRepository = processTemplateRepository;
    }

    /**
     * Save a processTemplate.
     *
     * @param processTemplate the entity to save.
     * @return the persisted entity.
     */
    public ProcessTemplate save(ProcessTemplate processTemplate) {
        log.debug("Request to save ProcessTemplate : {}", processTemplate);
        return processTemplateRepository.save(processTemplate);
    }

    /**
     * Get all the processTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessTemplates");
        return processTemplateRepository.findAll(pageable);
    }


    /**
     * Get one processTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessTemplate> findOne(Long id) {
        log.debug("Request to get ProcessTemplate : {}", id);
        return processTemplateRepository.findById(id);
    }

    /**
     * Delete the processTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessTemplate : {}", id);
        processTemplateRepository.deleteById(id);
    }
}
