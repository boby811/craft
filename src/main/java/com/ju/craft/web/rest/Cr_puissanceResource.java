package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_puissance;
import com.ju.craft.repository.Cr_puissanceRepository;
import com.ju.craft.repository.search.Cr_puissanceSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_puissanceDTO;
import com.ju.craft.web.rest.mapper.Cr_puissanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Cr_puissance.
 */
@RestController
@RequestMapping("/api")
public class Cr_puissanceResource {

    private final Logger log = LoggerFactory.getLogger(Cr_puissanceResource.class);
        
    @Inject
    private Cr_puissanceRepository cr_puissanceRepository;
    
    @Inject
    private Cr_puissanceMapper cr_puissanceMapper;
    
    @Inject
    private Cr_puissanceSearchRepository cr_puissanceSearchRepository;
    
    /**
     * POST  /cr_puissances -> Create a new cr_puissance.
     */
    @RequestMapping(value = "/cr_puissances",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_puissanceDTO> createCr_puissance(@Valid @RequestBody Cr_puissanceDTO cr_puissanceDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_puissance : {}", cr_puissanceDTO);
        if (cr_puissanceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_puissance", "idexists", "A new cr_puissance cannot already have an ID")).body(null);
        }
        Cr_puissance cr_puissance = cr_puissanceMapper.cr_puissanceDTOToCr_puissance(cr_puissanceDTO);
        cr_puissance = cr_puissanceRepository.save(cr_puissance);
        Cr_puissanceDTO result = cr_puissanceMapper.cr_puissanceToCr_puissanceDTO(cr_puissance);
        cr_puissanceSearchRepository.save(cr_puissance);
        return ResponseEntity.created(new URI("/api/cr_puissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_puissance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_puissances -> Updates an existing cr_puissance.
     */
    @RequestMapping(value = "/cr_puissances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_puissanceDTO> updateCr_puissance(@Valid @RequestBody Cr_puissanceDTO cr_puissanceDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_puissance : {}", cr_puissanceDTO);
        if (cr_puissanceDTO.getId() == null) {
            return createCr_puissance(cr_puissanceDTO);
        }
        Cr_puissance cr_puissance = cr_puissanceMapper.cr_puissanceDTOToCr_puissance(cr_puissanceDTO);
        cr_puissance = cr_puissanceRepository.save(cr_puissance);
        Cr_puissanceDTO result = cr_puissanceMapper.cr_puissanceToCr_puissanceDTO(cr_puissance);
        cr_puissanceSearchRepository.save(cr_puissance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_puissance", cr_puissanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_puissances -> get all the cr_puissances.
     */
    @RequestMapping(value = "/cr_puissances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_puissanceDTO> getAllCr_puissances() {
        log.debug("REST request to get all Cr_puissances");
        return cr_puissanceRepository.findAll().stream()
            .map(cr_puissanceMapper::cr_puissanceToCr_puissanceDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_puissances/:id -> get the "id" cr_puissance.
     */
    @RequestMapping(value = "/cr_puissances/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_puissanceDTO> getCr_puissance(@PathVariable Long id) {
        log.debug("REST request to get Cr_puissance : {}", id);
        Cr_puissance cr_puissance = cr_puissanceRepository.findOne(id);
        Cr_puissanceDTO cr_puissanceDTO = cr_puissanceMapper.cr_puissanceToCr_puissanceDTO(cr_puissance);
        return Optional.ofNullable(cr_puissanceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_puissances/:id -> delete the "id" cr_puissance.
     */
    @RequestMapping(value = "/cr_puissances/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_puissance(@PathVariable Long id) {
        log.debug("REST request to delete Cr_puissance : {}", id);
        cr_puissanceRepository.delete(id);
        cr_puissanceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_puissance", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_puissances/:query -> search for the cr_puissance corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_puissances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_puissanceDTO> searchCr_puissances(@PathVariable String query) {
        log.debug("REST request to search Cr_puissances for query {}", query);
        return StreamSupport
            .stream(cr_puissanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_puissanceMapper::cr_puissanceToCr_puissanceDTO)
            .collect(Collectors.toList());
    }
}
