package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_systeme;
import com.ju.craft.repository.Cr_systemeRepository;
import com.ju.craft.repository.search.Cr_systemeSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Cr_systeme.
 */
@RestController
@RequestMapping("/api")
public class Cr_systemeResource {

    private final Logger log = LoggerFactory.getLogger(Cr_systemeResource.class);
        
    @Inject
    private Cr_systemeRepository cr_systemeRepository;
    
    @Inject
    private Cr_systemeSearchRepository cr_systemeSearchRepository;
    
    /**
     * POST  /cr_systemes -> Create a new cr_systeme.
     */
    @RequestMapping(value = "/cr_systemes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_systeme> createCr_systeme(@Valid @RequestBody Cr_systeme cr_systeme) throws URISyntaxException {
        log.debug("REST request to save Cr_systeme : {}", cr_systeme);
        if (cr_systeme.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_systeme", "idexists", "A new cr_systeme cannot already have an ID")).body(null);
        }
        Cr_systeme result = cr_systemeRepository.save(cr_systeme);
        cr_systemeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cr_systemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_systeme", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_systemes -> Updates an existing cr_systeme.
     */
    @RequestMapping(value = "/cr_systemes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_systeme> updateCr_systeme(@Valid @RequestBody Cr_systeme cr_systeme) throws URISyntaxException {
        log.debug("REST request to update Cr_systeme : {}", cr_systeme);
        if (cr_systeme.getId() == null) {
            return createCr_systeme(cr_systeme);
        }
        Cr_systeme result = cr_systemeRepository.save(cr_systeme);
        cr_systemeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_systeme", cr_systeme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_systemes -> get all the cr_systemes.
     */
    @RequestMapping(value = "/cr_systemes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cr_systeme>> getAllCr_systemes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cr_systemes");
        Page<Cr_systeme> page = cr_systemeRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cr_systemes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cr_systemes/:id -> get the "id" cr_systeme.
     */
    @RequestMapping(value = "/cr_systemes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_systeme> getCr_systeme(@PathVariable Long id) {
        log.debug("REST request to get Cr_systeme : {}", id);
        Cr_systeme cr_systeme = cr_systemeRepository.findOne(id);
        return Optional.ofNullable(cr_systeme)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_systemes/:id -> delete the "id" cr_systeme.
     */
    @RequestMapping(value = "/cr_systemes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_systeme(@PathVariable Long id) {
        log.debug("REST request to delete Cr_systeme : {}", id);
        cr_systemeRepository.delete(id);
        cr_systemeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_systeme", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_systemes/:query -> search for the cr_systeme corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_systemes/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_systeme> searchCr_systemes(@PathVariable String query) {
    	log.debug("REST request to search Cr_systemes for query {}", query.toString());
    	log.debug("REST query test:",matchQuery("sy_nom_fr_fr", query));
        return StreamSupport
            .stream(cr_systemeSearchRepository.search(matchPhraseQuery("sy_nom_fr_fr",query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
