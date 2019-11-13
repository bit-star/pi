package com.lazulite.pi.service;

import com.lazulite.pi.domain.ProcessInstanceEvent;
import com.lazulite.pi.repository.ProcessInstanceEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessInstanceEvent}.
 */
@Service
@Transactional
public class ProcessInstanceEventService {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceEventService.class);

    private final ProcessInstanceEventRepository processInstanceEventRepository;

    public ProcessInstanceEventService(ProcessInstanceEventRepository processInstanceEventRepository) {
        this.processInstanceEventRepository = processInstanceEventRepository;
    }

    /**
     * Save a processInstanceEvent.
     *
     * @param processInstanceEvent the entity to save.
     * @return the persisted entity.
     */
    public ProcessInstanceEvent save(ProcessInstanceEvent processInstanceEvent) {
        log.debug("Request to save ProcessInstanceEvent : {}", processInstanceEvent);
        return processInstanceEventRepository.save(processInstanceEvent);
    }

    /**
     * Get all the processInstanceEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessInstanceEvent> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessInstanceEvents");
        return processInstanceEventRepository.findAll(pageable);
    }


    /**
     * Get one processInstanceEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessInstanceEvent> findOne(Long id) {
        log.debug("Request to get ProcessInstanceEvent : {}", id);
        return processInstanceEventRepository.findById(id);
    }

    /**
     * Delete the processInstanceEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessInstanceEvent : {}", id);
        processInstanceEventRepository.deleteById(id);
    }
}
