package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_corps;
import com.ju.craft.repository.Cr_corpsRepository;
import com.ju.craft.repository.search.Cr_corpsSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_corpsDTO;
import com.ju.craft.web.rest.mapper.Cr_corpsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Cr_corps.
 */
@RestController
@RequestMapping("/api")
public class Cr_corpsResource {

    private final Logger log = LoggerFactory.getLogger(Cr_corpsResource.class);
        
    @Inject
    private Cr_corpsRepository cr_corpsRepository;
    
    @Inject
    private Cr_corpsMapper cr_corpsMapper;
    
    @Inject
    private Cr_corpsSearchRepository cr_corpsSearchRepository;
    
    /**
     * POST  /cr_corpss -> Create a new cr_corps.
     */
    @RequestMapping(value = "/cr_corpss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_corpsDTO> createCr_corps(@RequestBody Cr_corpsDTO cr_corpsDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_corps : {}", cr_corpsDTO);
        if (cr_corpsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_corps", "idexists", "A new cr_corps cannot already have an ID")).body(null);
        }
        Cr_corps cr_corps = cr_corpsMapper.cr_corpsDTOToCr_corps(cr_corpsDTO);
        cr_corps = cr_corpsRepository.save(cr_corps);
        Cr_corpsDTO result = cr_corpsMapper.cr_corpsToCr_corpsDTO(cr_corps);
        cr_corpsSearchRepository.save(cr_corps);
        return ResponseEntity.created(new URI("/api/cr_corpss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_corps", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_corpss -> Updates an existing cr_corps.
     */
    @RequestMapping(value = "/cr_corpss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_corpsDTO> updateCr_corps(@RequestBody Cr_corpsDTO cr_corpsDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_corps : {}", cr_corpsDTO);
        if (cr_corpsDTO.getId() == null) {
            return createCr_corps(cr_corpsDTO);
        }
        Cr_corps cr_corps = cr_corpsMapper.cr_corpsDTOToCr_corps(cr_corpsDTO);
        cr_corps = cr_corpsRepository.save(cr_corps);
        Cr_corpsDTO result = cr_corpsMapper.cr_corpsToCr_corpsDTO(cr_corps);
        cr_corpsSearchRepository.save(cr_corps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_corps", cr_corpsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_corpss -> get all the cr_corpss.
     */
    @RequestMapping(value = "/cr_corpss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_corpsDTO> getAllCr_corpss() {
        log.debug("REST request to get all Cr_corpss");
        return cr_corpsRepository.findAll().stream()
            .map(cr_corpsMapper::cr_corpsToCr_corpsDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_corpss/:id -> get the "id" cr_corps.
     */
    @RequestMapping(value = "/cr_corpss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_corpsDTO> getCr_corps(@PathVariable Long id) {
        log.debug("REST request to get Cr_corps : {}", id);
        Cr_corps cr_corps = cr_corpsRepository.findOne(id);
        Cr_corpsDTO cr_corpsDTO = cr_corpsMapper.cr_corpsToCr_corpsDTO(cr_corps);
        return Optional.ofNullable(cr_corpsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_corpss/:id -> delete the "id" cr_corps.
     */
    @RequestMapping(value = "/cr_corpss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_corps(@PathVariable Long id) {
        log.debug("REST request to delete Cr_corps : {}", id);
        cr_corpsRepository.delete(id);
        cr_corpsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_corps", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_corpss/:query -> search for the cr_corps corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_corpss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_corpsDTO> searchCr_corpss(@PathVariable String query) {
        log.debug("REST request to search Cr_corpss for query {}", query);
        return StreamSupport
            .stream(cr_corpsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_corpsMapper::cr_corpsToCr_corpsDTO)
            .collect(Collectors.toList());
    }
}
