package com.lazulite.pi.web.rest;

import com.lazulite.pi.domain.FormComponentValues;
import com.lazulite.pi.service.FormComponentValuesService;
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
 * REST controller for managing {@link com.lazulite.pi.domain.FormComponentValues}.
 */
@RestController
@RequestMapping("/api")
public class FormComponentValuesResource {

    private final Logger log = LoggerFactory.getLogger(FormComponentValuesResource.class);

    private static final String ENTITY_NAME = "formComponentValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormComponentValuesService formComponentValuesService;

    public FormComponentValuesResource(FormComponentValuesService formComponentValuesService) {
        this.formComponentValuesService = formComponentValuesService;
    }

    /**
     * {@code POST  /form-component-values} : Create a new formComponentValues.
     *
     * @param formComponentValues the formComponentValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formComponentValues, or with status {@code 400 (Bad Request)} if the formComponentValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/form-component-values")
    public ResponseEntity<FormComponentValues> createFormComponentValues(@RequestBody FormComponentValues formComponentValues) throws URISyntaxException {
        log.debug("REST request to save FormComponentValues : {}", formComponentValues);
        if (formComponentValues.getId() != null) {
            throw new BadRequestAlertException("A new formComponentValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormComponentValues result = formComponentValuesService.save(formComponentValues);
        return ResponseEntity.created(new URI("/api/form-component-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /form-component-values} : Updates an existing formComponentValues.
     *
     * @param formComponentValues the formComponentValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formComponentValues,
     * or with status {@code 400 (Bad Request)} if the formComponentValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formComponentValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/form-component-values")
    public ResponseEntity<FormComponentValues> updateFormComponentValues(@RequestBody FormComponentValues formComponentValues) throws URISyntaxException {
        log.debug("REST request to update FormComponentValues : {}", formComponentValues);
        if (formComponentValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormComponentValues result = formComponentValuesService.save(formComponentValues);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formComponentValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /form-component-values} : get all the formComponentValues.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formComponentValues in body.
     */
    @GetMapping("/form-component-values")
    public ResponseEntity<List<FormComponentValues>> getAllFormComponentValues(Pageable pageable) {
        log.debug("REST request to get a page of FormComponentValues");
        Page<FormComponentValues> page = formComponentValuesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /form-component-values/:id} : get the "id" formComponentValues.
     *
     * @param id the id of the formComponentValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formComponentValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/form-component-values/{id}")
    public ResponseEntity<FormComponentValues> getFormComponentValues(@PathVariable Long id) {
        log.debug("REST request to get FormComponentValues : {}", id);
        Optional<FormComponentValues> formComponentValues = formComponentValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formComponentValues);
    }

    /**
     * {@code DELETE  /form-component-values/:id} : delete the "id" formComponentValues.
     *
     * @param id the id of the formComponentValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/form-component-values/{id}")
    public ResponseEntity<Void> deleteFormComponentValues(@PathVariable Long id) {
        log.debug("REST request to delete FormComponentValues : {}", id);
        formComponentValuesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
