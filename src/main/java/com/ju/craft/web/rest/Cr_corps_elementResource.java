package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_corps_element;
import com.ju.craft.repository.Cr_corps_elementRepository;
import com.ju.craft.repository.search.Cr_corps_elementSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.util.PaginationUtil;
import com.ju.craft.web.rest.dto.Cr_corps_elementDTO;
import com.ju.craft.web.rest.mapper.Cr_corps_elementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Cr_corps_element.
 */
@RestController
@RequestMapping("/api")
public class Cr_corps_elementResource {

    private final Logger log = LoggerFactory.getLogger(Cr_corps_elementResource.class);
        
    @Inject
    private Cr_corps_elementRepository cr_corps_elementRepository;
    
    @Inject
    private Cr_corps_elementMapper cr_corps_elementMapper;
    
    @Inject
    private Cr_corps_elementSearchRepository cr_corps_elementSearchRepository;
    
    /**
     * POST  /cr_corps_elements -> Create a new cr_corps_element.
     */
    @RequestMapping(value = "/cr_corps_elements",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_corps_elementDTO> createCr_corps_element(@RequestBody Cr_corps_elementDTO cr_corps_elementDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_corps_element : {}", cr_corps_elementDTO);
        if (cr_corps_elementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_corps_element", "idexists", "A new cr_corps_element cannot already have an ID")).body(null);
        }
        Cr_corps_element cr_corps_element = cr_corps_elementMapper.cr_corps_elementDTOToCr_corps_element(cr_corps_elementDTO);
        cr_corps_element = cr_corps_elementRepository.save(cr_corps_element);
        Cr_corps_elementDTO result = cr_corps_elementMapper.cr_corps_elementToCr_corps_elementDTO(cr_corps_element);
        cr_corps_elementSearchRepository.save(cr_corps_element);
        return ResponseEntity.created(new URI("/api/cr_corps_elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_corps_element", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_corps_elements -> Updates an existing cr_corps_element.
     */
    @RequestMapping(value = "/cr_corps_elements",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_corps_elementDTO> updateCr_corps_element(@RequestBody Cr_corps_elementDTO cr_corps_elementDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_corps_element : {}", cr_corps_elementDTO);
        if (cr_corps_elementDTO.getId() == null) {
            return createCr_corps_element(cr_corps_elementDTO);
        }
        Cr_corps_element cr_corps_element = cr_corps_elementMapper.cr_corps_elementDTOToCr_corps_element(cr_corps_elementDTO);
        cr_corps_element = cr_corps_elementRepository.save(cr_corps_element);
        Cr_corps_elementDTO result = cr_corps_elementMapper.cr_corps_elementToCr_corps_elementDTO(cr_corps_element);
        cr_corps_elementSearchRepository.save(cr_corps_element);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_corps_element", cr_corps_elementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_corps_elements -> get all the cr_corps_elements.
     */
    @RequestMapping(value = "/cr_corps_elements",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<Cr_corps_elementDTO>> getAllCr_corps_elements(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cr_corps_elements");
        Page<Cr_corps_element> page = cr_corps_elementRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cr_corps_elements");
        return new ResponseEntity<>(page.getContent().stream()
            .map(cr_corps_elementMapper::cr_corps_elementToCr_corps_elementDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /cr_corps_elements/:id -> get the "id" cr_corps_element.
     */
    @RequestMapping(value = "/cr_corps_elements/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_corps_elementDTO> getCr_corps_element(@PathVariable Long id) {
        log.debug("REST request to get Cr_corps_element : {}", id);
        Cr_corps_element cr_corps_element = cr_corps_elementRepository.findOne(id);
        Cr_corps_elementDTO cr_corps_elementDTO = cr_corps_elementMapper.cr_corps_elementToCr_corps_elementDTO(cr_corps_element);
        return Optional.ofNullable(cr_corps_elementDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_corps_elements/:id -> delete the "id" cr_corps_element.
     */
    @RequestMapping(value = "/cr_corps_elements/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_corps_element(@PathVariable Long id) {
        log.debug("REST request to delete Cr_corps_element : {}", id);
        cr_corps_elementRepository.delete(id);
        cr_corps_elementSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_corps_element", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_corps_elements/:query -> search for the cr_corps_element corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_corps_elements/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_corps_elementDTO> searchCr_corps_elements(@PathVariable String query) {
        log.debug("REST request to search Cr_corps_elements for query {}", query);
        return StreamSupport
            .stream(cr_corps_elementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_corps_elementMapper::cr_corps_elementToCr_corps_elementDTO)
            .collect(Collectors.toList());
    }
}
