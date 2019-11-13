package com.lazulite.pi.web.rest;

import com.lazulite.pi.domain.ProcessInstance;
import com.lazulite.pi.service.ProcessInstanceService;
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
 * REST controller for managing {@link com.lazulite.pi.domain.ProcessInstance}.
 */
@RestController
@RequestMapping("/api")
public class ProcessInstanceResource {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceResource.class);

    private static final String ENTITY_NAME = "processInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessInstanceService processInstanceService;

    public ProcessInstanceResource(ProcessInstanceService processInstanceService) {
        this.processInstanceService = processInstanceService;
    }

    /**
     * {@code POST  /process-instances} : Create a new processInstance.
     *
     * @param processInstance the processInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processInstance, or with status {@code 400 (Bad Request)} if the processInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-instances")
    public ResponseEntity<ProcessInstance> createProcessInstance(@RequestBody ProcessInstance processInstance) throws URISyntaxException {
        log.debug("REST request to save ProcessInstance : {}", processInstance);
        if (processInstance.getId() != null) {
            throw new BadRequestAlertException("A new processInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessInstance result = processInstanceService.save(processInstance);
        return ResponseEntity.created(new URI("/api/process-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-instances} : Updates an existing processInstance.
     *
     * @param processInstance the processInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processInstance,
     * or with status {@code 400 (Bad Request)} if the processInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-instances")
    public ResponseEntity<ProcessInstance> updateProcessInstance(@RequestBody ProcessInstance processInstance) throws URISyntaxException {
        log.debug("REST request to update ProcessInstance : {}", processInstance);
        if (processInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessInstance result = processInstanceService.save(processInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /process-instances} : get all the processInstances.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processInstances in body.
     */
    @GetMapping("/process-instances")
    public ResponseEntity<List<ProcessInstance>> getAllProcessInstances(Pageable pageable) {
        log.debug("REST request to get a page of ProcessInstances");
        Page<ProcessInstance> page = processInstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-instances/:id} : get the "id" processInstance.
     *
     * @param id the id of the processInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instances/{id}")
    public ResponseEntity<ProcessInstance> getProcessInstance(@PathVariable Long id) {
        log.debug("REST request to get ProcessInstance : {}", id);
        Optional<ProcessInstance> processInstance = processInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processInstance);
    }

    /**
     * {@code DELETE  /process-instances/:id} : delete the "id" processInstance.
     *
     * @param id the id of the processInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-instances/{id}")
    public ResponseEntity<Void> deleteProcessInstance(@PathVariable Long id) {
        log.debug("REST request to delete ProcessInstance : {}", id);
        processInstanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
