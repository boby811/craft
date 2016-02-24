package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_type_corps;
import com.ju.craft.repository.Cr_type_corpsRepository;
import com.ju.craft.repository.search.Cr_type_corpsSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_type_corpsDTO;
import com.ju.craft.web.rest.mapper.Cr_type_corpsMapper;
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
 * REST controller for managing Cr_type_corps.
 */
@RestController
@RequestMapping("/api")
public class Cr_type_corpsResource {

    private final Logger log = LoggerFactory.getLogger(Cr_type_corpsResource.class);
        
    @Inject
    private Cr_type_corpsRepository cr_type_corpsRepository;
    
    @Inject
    private Cr_type_corpsMapper cr_type_corpsMapper;
    
    @Inject
    private Cr_type_corpsSearchRepository cr_type_corpsSearchRepository;
    
    /**
     * POST  /cr_type_corpss -> Create a new cr_type_corps.
     */
    @RequestMapping(value = "/cr_type_corpss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_type_corpsDTO> createCr_type_corps(@Valid @RequestBody Cr_type_corpsDTO cr_type_corpsDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_type_corps : {}", cr_type_corpsDTO);
        if (cr_type_corpsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_type_corps", "idexists", "A new cr_type_corps cannot already have an ID")).body(null);
        }
        Cr_type_corps cr_type_corps = cr_type_corpsMapper.cr_type_corpsDTOToCr_type_corps(cr_type_corpsDTO);
        cr_type_corps = cr_type_corpsRepository.save(cr_type_corps);
        Cr_type_corpsDTO result = cr_type_corpsMapper.cr_type_corpsToCr_type_corpsDTO(cr_type_corps);
        cr_type_corpsSearchRepository.save(cr_type_corps);
        return ResponseEntity.created(new URI("/api/cr_type_corpss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_type_corps", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_type_corpss -> Updates an existing cr_type_corps.
     */
    @RequestMapping(value = "/cr_type_corpss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_type_corpsDTO> updateCr_type_corps(@Valid @RequestBody Cr_type_corpsDTO cr_type_corpsDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_type_corps : {}", cr_type_corpsDTO);
        if (cr_type_corpsDTO.getId() == null) {
            return createCr_type_corps(cr_type_corpsDTO);
        }
        Cr_type_corps cr_type_corps = cr_type_corpsMapper.cr_type_corpsDTOToCr_type_corps(cr_type_corpsDTO);
        cr_type_corps = cr_type_corpsRepository.save(cr_type_corps);
        Cr_type_corpsDTO result = cr_type_corpsMapper.cr_type_corpsToCr_type_corpsDTO(cr_type_corps);
        cr_type_corpsSearchRepository.save(cr_type_corps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_type_corps", cr_type_corpsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_type_corpss -> get all the cr_type_corpss.
     */
    @RequestMapping(value = "/cr_type_corpss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_type_corpsDTO> getAllCr_type_corpss() {
        log.debug("REST request to get all Cr_type_corpss");
        return cr_type_corpsRepository.findAll().stream()
            .map(cr_type_corpsMapper::cr_type_corpsToCr_type_corpsDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_type_corpss/:id -> get the "id" cr_type_corps.
     */
    @RequestMapping(value = "/cr_type_corpss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_type_corpsDTO> getCr_type_corps(@PathVariable Long id) {
        log.debug("REST request to get Cr_type_corps : {}", id);
        Cr_type_corps cr_type_corps = cr_type_corpsRepository.findOne(id);
        Cr_type_corpsDTO cr_type_corpsDTO = cr_type_corpsMapper.cr_type_corpsToCr_type_corpsDTO(cr_type_corps);
        return Optional.ofNullable(cr_type_corpsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_type_corpss/:id -> delete the "id" cr_type_corps.
     */
    @RequestMapping(value = "/cr_type_corpss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_type_corps(@PathVariable Long id) {
        log.debug("REST request to delete Cr_type_corps : {}", id);
        cr_type_corpsRepository.delete(id);
        cr_type_corpsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_type_corps", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_type_corpss/:query -> search for the cr_type_corps corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_type_corpss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_type_corpsDTO> searchCr_type_corpss(@PathVariable String query) {
        log.debug("REST request to search Cr_type_corpss for query {}", query);
        return StreamSupport
            .stream(cr_type_corpsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_type_corpsMapper::cr_type_corpsToCr_type_corpsDTO)
            .collect(Collectors.toList());
    }
}
