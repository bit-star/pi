package com.lazulite.pi.web.rest;

import com.lazulite.pi.domain.ProcessMsgTask;
import com.lazulite.pi.service.ProcessMsgTaskService;
import com.lazulite.pi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lazulite.pi.domain.ProcessMsgTask}.
 */
@RestController
@RequestMapping("/api")
public class ProcessMsgTaskResource {

    private final Logger log = LoggerFactory.getLogger(ProcessMsgTaskResource.class);

    private static final String ENTITY_NAME = "processMsgTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessMsgTaskService processMsgTaskService;

    public ProcessMsgTaskResource(ProcessMsgTaskService processMsgTaskService) {
        this.processMsgTaskService = processMsgTaskService;
    }

    /**
     * {@code POST  /process-msg-tasks} : Create a new processMsgTask.
     *
     * @param processMsgTask the processMsgTask to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processMsgTask, or with status {@code 400 (Bad Request)} if the processMsgTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-msg-tasks")
    public ResponseEntity<ProcessMsgTask> createProcessMsgTask(@RequestBody ProcessMsgTask processMsgTask) throws URISyntaxException {
        log.debug("REST request to save ProcessMsgTask : {}", processMsgTask);
        if (processMsgTask.getId() != null) {
            throw new BadRequestAlertException("A new processMsgTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessMsgTask result = processMsgTaskService.save(processMsgTask);
        return ResponseEntity.created(new URI("/api/process-msg-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-msg-tasks} : Updates an existing processMsgTask.
     *
     * @param processMsgTask the processMsgTask to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processMsgTask,
     * or with status {@code 400 (Bad Request)} if the processMsgTask is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processMsgTask couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-msg-tasks")
    public ResponseEntity<ProcessMsgTask> updateProcessMsgTask(@RequestBody ProcessMsgTask processMsgTask) throws URISyntaxException {
        log.debug("REST request to update ProcessMsgTask : {}", processMsgTask);
        if (processMsgTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessMsgTask result = processMsgTaskService.save(processMsgTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processMsgTask.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /process-msg-tasks} : get all the processMsgTasks.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processMsgTasks in body.
     */
    @GetMapping("/process-msg-tasks")
    public ResponseEntity<List<ProcessMsgTask>> getAllProcessMsgTasks(Pageable pageable) {
        log.debug("REST request to get a page of ProcessMsgTasks");
        Page<ProcessMsgTask> page = processMsgTaskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-msg-tasks/:id} : get the "id" processMsgTask.
     *
     * @param id the id of the processMsgTask to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processMsgTask, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-msg-tasks/{id}")
    public ResponseEntity<ProcessMsgTask> getProcessMsgTask(@PathVariable Long id) {
        log.debug("REST request to get ProcessMsgTask : {}", id);
        Optional<ProcessMsgTask> processMsgTask = processMsgTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processMsgTask);
    }

    /**
     * {@code DELETE  /process-msg-tasks/:id} : delete the "id" processMsgTask.
     *
     * @param id the id of the processMsgTask to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-msg-tasks/{id}")
    public ResponseEntity<Void> deleteProcessMsgTask(@PathVariable Long id) {
        log.debug("REST request to delete ProcessMsgTask : {}", id);
        processMsgTaskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
