package com.lazulite.pi.service;

import com.lazulite.pi.domain.ProcessInstance;
import com.lazulite.pi.repository.ProcessInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessInstance}.
 */
@Service
@Transactional
public class ProcessInstanceService {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceService.class);

    private final ProcessInstanceRepository processInstanceRepository;

    public ProcessInstanceService(ProcessInstanceRepository processInstanceRepository) {
        this.processInstanceRepository = processInstanceRepository;
    }

    /**
     * Save a processInstance.
     *
     * @param processInstance the entity to save.
     * @return the persisted entity.
     */
    public ProcessInstance save(ProcessInstance processInstance) {
        log.debug("Request to save ProcessInstance : {}", processInstance);
        return processInstanceRepository.save(processInstance);
    }

    /**
     * Get all the processInstances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessInstance> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessInstances");
        return processInstanceRepository.findAll(pageable);
    }


    /**
     * Get one processInstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessInstance> findOne(Long id) {
        log.debug("Request to get ProcessInstance : {}", id);
        return processInstanceRepository.findById(id);
    }

    /**
     * Delete the processInstance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessInstance : {}", id);
        processInstanceRepository.deleteById(id);
    }
}
