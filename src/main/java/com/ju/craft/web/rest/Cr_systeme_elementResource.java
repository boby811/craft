package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_systeme_element;
import com.ju.craft.repository.Cr_systeme_elementRepository;
import com.ju.craft.repository.search.Cr_systeme_elementSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_systeme_elementDTO;
import com.ju.craft.web.rest.mapper.Cr_systeme_elementMapper;
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
 * REST controller for managing Cr_systeme_element.
 */
@RestController
@RequestMapping("/api")
public class Cr_systeme_elementResource {

    private final Logger log = LoggerFactory.getLogger(Cr_systeme_elementResource.class);
        
    @Inject
    private Cr_systeme_elementRepository cr_systeme_elementRepository;
    
    @Inject
    private Cr_systeme_elementMapper cr_systeme_elementMapper;
    
    @Inject
    private Cr_systeme_elementSearchRepository cr_systeme_elementSearchRepository;
    
    /**
     * POST  /cr_systeme_elements -> Create a new cr_systeme_element.
     */
    @RequestMapping(value = "/cr_systeme_elements",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_systeme_elementDTO> createCr_systeme_element(@RequestBody Cr_systeme_elementDTO cr_systeme_elementDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_systeme_element : {}", cr_systeme_elementDTO);
        if (cr_systeme_elementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_systeme_element", "idexists", "A new cr_systeme_element cannot already have an ID")).body(null);
        }
        Cr_systeme_element cr_systeme_element = cr_systeme_elementMapper.cr_systeme_elementDTOToCr_systeme_element(cr_systeme_elementDTO);
        cr_systeme_element = cr_systeme_elementRepository.save(cr_systeme_element);
        Cr_systeme_elementDTO result = cr_systeme_elementMapper.cr_systeme_elementToCr_systeme_elementDTO(cr_systeme_element);
        cr_systeme_elementSearchRepository.save(cr_systeme_element);
        return ResponseEntity.created(new URI("/api/cr_systeme_elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_systeme_element", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_systeme_elements -> Updates an existing cr_systeme_element.
     */
    @RequestMapping(value = "/cr_systeme_elements",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_systeme_elementDTO> updateCr_systeme_element(@RequestBody Cr_systeme_elementDTO cr_systeme_elementDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_systeme_element : {}", cr_systeme_elementDTO);
        if (cr_systeme_elementDTO.getId() == null) {
            return createCr_systeme_element(cr_systeme_elementDTO);
        }
        Cr_systeme_element cr_systeme_element = cr_systeme_elementMapper.cr_systeme_elementDTOToCr_systeme_element(cr_systeme_elementDTO);
        cr_systeme_element = cr_systeme_elementRepository.save(cr_systeme_element);
        Cr_systeme_elementDTO result = cr_systeme_elementMapper.cr_systeme_elementToCr_systeme_elementDTO(cr_systeme_element);
        cr_systeme_elementSearchRepository.save(cr_systeme_element);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_systeme_element", cr_systeme_elementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_systeme_elements -> get all the cr_systeme_elements.
     */
    @RequestMapping(value = "/cr_systeme_elements",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_systeme_elementDTO> getAllCr_systeme_elements() {
        log.debug("REST request to get all Cr_systeme_elements");
        return cr_systeme_elementRepository.findAll().stream()
            .map(cr_systeme_elementMapper::cr_systeme_elementToCr_systeme_elementDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_systeme_elements/:id -> get the "id" cr_systeme_element.
     */
    @RequestMapping(value = "/cr_systeme_elements/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_systeme_elementDTO> getCr_systeme_element(@PathVariable Long id) {
        log.debug("REST request to get Cr_systeme_element : {}", id);
        Cr_systeme_element cr_systeme_element = cr_systeme_elementRepository.findOne(id);
        Cr_systeme_elementDTO cr_systeme_elementDTO = cr_systeme_elementMapper.cr_systeme_elementToCr_systeme_elementDTO(cr_systeme_element);
        return Optional.ofNullable(cr_systeme_elementDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_systeme_elements/:id -> delete the "id" cr_systeme_element.
     */
    @RequestMapping(value = "/cr_systeme_elements/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_systeme_element(@PathVariable Long id) {
        log.debug("REST request to delete Cr_systeme_element : {}", id);
        cr_systeme_elementRepository.delete(id);
        cr_systeme_elementSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_systeme_element", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_systeme_elements/:query -> search for the cr_systeme_element corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_systeme_elements/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_systeme_elementDTO> searchCr_systeme_elements(@PathVariable String query) {
        log.debug("REST request to search Cr_systeme_elements for query {}", query);
        return StreamSupport
            .stream(cr_systeme_elementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_systeme_elementMapper::cr_systeme_elementToCr_systeme_elementDTO)
            .collect(Collectors.toList());
    }
}
