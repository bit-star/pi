package com.lazulite.pi.web.rest;

import com.lazulite.pi.domain.DdMessage;
import com.lazulite.pi.service.DdMessageService;
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
 * REST controller for managing {@link com.lazulite.pi.domain.DdMessage}.
 */
@RestController
@RequestMapping("/api")
public class DdMessageResource {

    private final Logger log = LoggerFactory.getLogger(DdMessageResource.class);

    private static final String ENTITY_NAME = "ddMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DdMessageService ddMessageService;

    public DdMessageResource(DdMessageService ddMessageService) {
        this.ddMessageService = ddMessageService;
    }

    /**
     * {@code POST  /dd-messages} : Create a new ddMessage.
     *
     * @param ddMessage the ddMessage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ddMessage, or with status {@code 400 (Bad Request)} if the ddMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dd-messages")
    public ResponseEntity<DdMessage> createDdMessage(@RequestBody DdMessage ddMessage) throws URISyntaxException {
        log.debug("REST request to save DdMessage : {}", ddMessage);
        if (ddMessage.getId() != null) {
            throw new BadRequestAlertException("A new ddMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DdMessage result = ddMessageService.save(ddMessage);
        return ResponseEntity.created(new URI("/api/dd-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dd-messages} : Updates an existing ddMessage.
     *
     * @param ddMessage the ddMessage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ddMessage,
     * or with status {@code 400 (Bad Request)} if the ddMessage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ddMessage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dd-messages")
    public ResponseEntity<DdMessage> updateDdMessage(@RequestBody DdMessage ddMessage) throws URISyntaxException {
        log.debug("REST request to update DdMessage : {}", ddMessage);
        if (ddMessage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DdMessage result = ddMessageService.save(ddMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ddMessage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dd-messages} : get all the ddMessages.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ddMessages in body.
     */
    @GetMapping("/dd-messages")
    public ResponseEntity<List<DdMessage>> getAllDdMessages(Pageable pageable) {
        log.debug("REST request to get a page of DdMessages");
        Page<DdMessage> page = ddMessageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dd-messages/:id} : get the "id" ddMessage.
     *
     * @param id the id of the ddMessage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ddMessage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dd-messages/{id}")
    public ResponseEntity<DdMessage> getDdMessage(@PathVariable Long id) {
        log.debug("REST request to get DdMessage : {}", id);
        Optional<DdMessage> ddMessage = ddMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ddMessage);
    }

    /**
     * {@code DELETE  /dd-messages/:id} : delete the "id" ddMessage.
     *
     * @param id the id of the ddMessage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dd-messages/{id}")
    public ResponseEntity<Void> deleteDdMessage(@PathVariable Long id) {
        log.debug("REST request to delete DdMessage : {}", id);
        ddMessageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
