package com.lazulite.pi.service;

import com.lazulite.pi.domain.ProcessMsgTask;
import com.lazulite.pi.repository.ProcessMsgTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessMsgTask}.
 */
@Service
@Transactional
public class ProcessMsgTaskService {

    private final Logger log = LoggerFactory.getLogger(ProcessMsgTaskService.class);

    private final ProcessMsgTaskRepository processMsgTaskRepository;

    public ProcessMsgTaskService(ProcessMsgTaskRepository processMsgTaskRepository) {
        this.processMsgTaskRepository = processMsgTaskRepository;
    }

    /**
     * Save a processMsgTask.
     *
     * @param processMsgTask the entity to save.
     * @return the persisted entity.
     */
    public ProcessMsgTask save(ProcessMsgTask processMsgTask) {
        log.debug("Request to save ProcessMsgTask : {}", processMsgTask);
        return processMsgTaskRepository.save(processMsgTask);
    }

    /**
     * Get all the processMsgTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessMsgTask> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessMsgTasks");
        return processMsgTaskRepository.findAll(pageable);
    }


    /**
     * Get one processMsgTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessMsgTask> findOne(Long id) {
        log.debug("Request to get ProcessMsgTask : {}", id);
        return processMsgTaskRepository.findById(id);
    }

    /**
     * Delete the processMsgTask by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessMsgTask : {}", id);
        processMsgTaskRepository.deleteById(id);
    }
}
