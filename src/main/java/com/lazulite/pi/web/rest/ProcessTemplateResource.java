package com.lazulite.pi.web.rest;

import com.lazulite.pi.domain.ProcessTemplate;
import com.lazulite.pi.service.ProcessTemplateService;
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
 * REST controller for managing {@link com.lazulite.pi.domain.ProcessTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ProcessTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ProcessTemplateResource.class);

    private static final String ENTITY_NAME = "processTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessTemplateService processTemplateService;

    public ProcessTemplateResource(ProcessTemplateService processTemplateService) {
        this.processTemplateService = processTemplateService;
    }

    /**
     * {@code POST  /process-templates} : Create a new processTemplate.
     *
     * @param processTemplate the processTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processTemplate, or with status {@code 400 (Bad Request)} if the processTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-templates")
    public ResponseEntity<ProcessTemplate> createProcessTemplate(@RequestBody ProcessTemplate processTemplate) throws URISyntaxException {
        log.debug("REST request to save ProcessTemplate : {}", processTemplate);
        if (processTemplate.getId() != null) {
            throw new BadRequestAlertException("A new processTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessTemplate result = processTemplateService.save(processTemplate);
        return ResponseEntity.created(new URI("/api/process-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-templates} : Updates an existing processTemplate.
     *
     * @param processTemplate the processTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processTemplate,
     * or with status {@code 400 (Bad Request)} if the processTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-templates")
    public ResponseEntity<ProcessTemplate> updateProcessTemplate(@RequestBody ProcessTemplate processTemplate) throws URISyntaxException {
        log.debug("REST request to update ProcessTemplate : {}", processTemplate);
        if (processTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessTemplate result = processTemplateService.save(processTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /process-templates} : get all the processTemplates.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processTemplates in body.
     */
    @GetMapping("/process-templates")
    public ResponseEntity<List<ProcessTemplate>> getAllProcessTemplates(Pageable pageable) {
        log.debug("REST request to get a page of ProcessTemplates");
        Page<ProcessTemplate> page = processTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-templates/:id} : get the "id" processTemplate.
     *
     * @param id the id of the processTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-templates/{id}")
    public ResponseEntity<ProcessTemplate> getProcessTemplate(@PathVariable Long id) {
        log.debug("REST request to get ProcessTemplate : {}", id);
        Optional<ProcessTemplate> processTemplate = processTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processTemplate);
    }

    /**
     * {@code DELETE  /process-templates/:id} : delete the "id" processTemplate.
     *
     * @param id the id of the processTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-templates/{id}")
    public ResponseEntity<Void> deleteProcessTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ProcessTemplate : {}", id);
        processTemplateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
