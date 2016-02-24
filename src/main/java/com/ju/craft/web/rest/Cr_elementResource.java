package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_element;
import com.ju.craft.repository.Cr_elementRepository;
import com.ju.craft.repository.search.Cr_elementSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_elementDTO;
import com.ju.craft.web.rest.mapper.Cr_elementMapper;
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
 * REST controller for managing Cr_element.
 */
@RestController
@RequestMapping("/api")
public class Cr_elementResource {

    private final Logger log = LoggerFactory.getLogger(Cr_elementResource.class);
        
    @Inject
    private Cr_elementRepository cr_elementRepository;
    
    @Inject
    private Cr_elementMapper cr_elementMapper;
    
    @Inject
    private Cr_elementSearchRepository cr_elementSearchRepository;
    
    /**
     * POST  /cr_elements -> Create a new cr_element.
     */
    @RequestMapping(value = "/cr_elements",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_elementDTO> createCr_element(@Valid @RequestBody Cr_elementDTO cr_elementDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_element : {}", cr_elementDTO);
        if (cr_elementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_element", "idexists", "A new cr_element cannot already have an ID")).body(null);
        }
        Cr_element cr_element = cr_elementMapper.cr_elementDTOToCr_element(cr_elementDTO);
        cr_element = cr_elementRepository.save(cr_element);
        Cr_elementDTO result = cr_elementMapper.cr_elementToCr_elementDTO(cr_element);
        cr_elementSearchRepository.save(cr_element);
        return ResponseEntity.created(new URI("/api/cr_elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_element", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_elements -> Updates an existing cr_element.
     */
    @RequestMapping(value = "/cr_elements",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_elementDTO> updateCr_element(@Valid @RequestBody Cr_elementDTO cr_elementDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_element : {}", cr_elementDTO);
        if (cr_elementDTO.getId() == null) {
            return createCr_element(cr_elementDTO);
        }
        Cr_element cr_element = cr_elementMapper.cr_elementDTOToCr_element(cr_elementDTO);
        cr_element = cr_elementRepository.save(cr_element);
        Cr_elementDTO result = cr_elementMapper.cr_elementToCr_elementDTO(cr_element);
        cr_elementSearchRepository.save(cr_element);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_element", cr_elementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_elements -> get all the cr_elements.
     */
    @RequestMapping(value = "/cr_elements",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_elementDTO> getAllCr_elements() {
        log.debug("REST request to get all Cr_elements");
        return cr_elementRepository.findAllWithEagerRelationships().stream()
            .map(cr_elementMapper::cr_elementToCr_elementDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_elements/:id -> get the "id" cr_element.
     */
    @RequestMapping(value = "/cr_elements/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_elementDTO> getCr_element(@PathVariable Long id) {
        log.debug("REST request to get Cr_element : {}", id);
        Cr_element cr_element = cr_elementRepository.findOneWithEagerRelationships(id);
        Cr_elementDTO cr_elementDTO = cr_elementMapper.cr_elementToCr_elementDTO(cr_element);
        return Optional.ofNullable(cr_elementDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_elements/:id -> delete the "id" cr_element.
     */
    @RequestMapping(value = "/cr_elements/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_element(@PathVariable Long id) {
        log.debug("REST request to delete Cr_element : {}", id);
        cr_elementRepository.delete(id);
        cr_elementSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_element", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_elements/:query -> search for the cr_element corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_elements/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_elementDTO> searchCr_elements(@PathVariable String query) {
        log.debug("REST request to search Cr_elements for query {}", query);
        return StreamSupport
            .stream(cr_elementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_elementMapper::cr_elementToCr_elementDTO)
            .collect(Collectors.toList());
    }
}
