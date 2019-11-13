package com.lazulite.pi.web.rest;

import com.lazulite.pi.domain.ProcessInstanceEvent;
import com.lazulite.pi.service.ProcessInstanceEventService;
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
 * REST controller for managing {@link com.lazulite.pi.domain.ProcessInstanceEvent}.
 */
@RestController
@RequestMapping("/api")
public class ProcessInstanceEventResource {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceEventResource.class);

    private static final String ENTITY_NAME = "processInstanceEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessInstanceEventService processInstanceEventService;

    public ProcessInstanceEventResource(ProcessInstanceEventService processInstanceEventService) {
        this.processInstanceEventService = processInstanceEventService;
    }

    /**
     * {@code POST  /process-instance-events} : Create a new processInstanceEvent.
     *
     * @param processInstanceEvent the processInstanceEvent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processInstanceEvent, or with status {@code 400 (Bad Request)} if the processInstanceEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-instance-events")
    public ResponseEntity<ProcessInstanceEvent> createProcessInstanceEvent(@RequestBody ProcessInstanceEvent processInstanceEvent) throws URISyntaxException {
        log.debug("REST request to save ProcessInstanceEvent : {}", processInstanceEvent);
        if (processInstanceEvent.getId() != null) {
            throw new BadRequestAlertException("A new processInstanceEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessInstanceEvent result = processInstanceEventService.save(processInstanceEvent);
        return ResponseEntity.created(new URI("/api/process-instance-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-instance-events} : Updates an existing processInstanceEvent.
     *
     * @param processInstanceEvent the processInstanceEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processInstanceEvent,
     * or with status {@code 400 (Bad Request)} if the processInstanceEvent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processInstanceEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-instance-events")
    public ResponseEntity<ProcessInstanceEvent> updateProcessInstanceEvent(@RequestBody ProcessInstanceEvent processInstanceEvent) throws URISyntaxException {
        log.debug("REST request to update ProcessInstanceEvent : {}", processInstanceEvent);
        if (processInstanceEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessInstanceEvent result = processInstanceEventService.save(processInstanceEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processInstanceEvent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /process-instance-events} : get all the processInstanceEvents.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processInstanceEvents in body.
     */
    @GetMapping("/process-instance-events")
    public ResponseEntity<List<ProcessInstanceEvent>> getAllProcessInstanceEvents(Pageable pageable) {
        log.debug("REST request to get a page of ProcessInstanceEvents");
        Page<ProcessInstanceEvent> page = processInstanceEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-instance-events/:id} : get the "id" processInstanceEvent.
     *
     * @param id the id of the processInstanceEvent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInstanceEvent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instance-events/{id}")
    public ResponseEntity<ProcessInstanceEvent> getProcessInstanceEvent(@PathVariable Long id) {
        log.debug("REST request to get ProcessInstanceEvent : {}", id);
        Optional<ProcessInstanceEvent> processInstanceEvent = processInstanceEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processInstanceEvent);
    }

    /**
     * {@code DELETE  /process-instance-events/:id} : delete the "id" processInstanceEvent.
     *
     * @param id the id of the processInstanceEvent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-instance-events/{id}")
    public ResponseEntity<Void> deleteProcessInstanceEvent(@PathVariable Long id) {
        log.debug("REST request to delete ProcessInstanceEvent : {}", id);
        processInstanceEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
