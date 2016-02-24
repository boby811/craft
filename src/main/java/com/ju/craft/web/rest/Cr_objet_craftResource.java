package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_objet_craft;
import com.ju.craft.repository.Cr_objet_craftRepository;
import com.ju.craft.repository.search.Cr_objet_craftSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_objet_craftDTO;
import com.ju.craft.web.rest.mapper.Cr_objet_craftMapper;
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
 * REST controller for managing Cr_objet_craft.
 */
@RestController
@RequestMapping("/api")
public class Cr_objet_craftResource {

    private final Logger log = LoggerFactory.getLogger(Cr_objet_craftResource.class);
        
    @Inject
    private Cr_objet_craftRepository cr_objet_craftRepository;
    
    @Inject
    private Cr_objet_craftMapper cr_objet_craftMapper;
    
    @Inject
    private Cr_objet_craftSearchRepository cr_objet_craftSearchRepository;
    
    /**
     * POST  /cr_objet_crafts -> Create a new cr_objet_craft.
     */
    @RequestMapping(value = "/cr_objet_crafts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_objet_craftDTO> createCr_objet_craft(@Valid @RequestBody Cr_objet_craftDTO cr_objet_craftDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_objet_craft : {}", cr_objet_craftDTO);
        if (cr_objet_craftDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_objet_craft", "idexists", "A new cr_objet_craft cannot already have an ID")).body(null);
        }
        Cr_objet_craft cr_objet_craft = cr_objet_craftMapper.cr_objet_craftDTOToCr_objet_craft(cr_objet_craftDTO);
        cr_objet_craft = cr_objet_craftRepository.save(cr_objet_craft);
        Cr_objet_craftDTO result = cr_objet_craftMapper.cr_objet_craftToCr_objet_craftDTO(cr_objet_craft);
        cr_objet_craftSearchRepository.save(cr_objet_craft);
        return ResponseEntity.created(new URI("/api/cr_objet_crafts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_objet_craft", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_objet_crafts -> Updates an existing cr_objet_craft.
     */
    @RequestMapping(value = "/cr_objet_crafts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_objet_craftDTO> updateCr_objet_craft(@Valid @RequestBody Cr_objet_craftDTO cr_objet_craftDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_objet_craft : {}", cr_objet_craftDTO);
        if (cr_objet_craftDTO.getId() == null) {
            return createCr_objet_craft(cr_objet_craftDTO);
        }
        Cr_objet_craft cr_objet_craft = cr_objet_craftMapper.cr_objet_craftDTOToCr_objet_craft(cr_objet_craftDTO);
        cr_objet_craft = cr_objet_craftRepository.save(cr_objet_craft);
        Cr_objet_craftDTO result = cr_objet_craftMapper.cr_objet_craftToCr_objet_craftDTO(cr_objet_craft);
        cr_objet_craftSearchRepository.save(cr_objet_craft);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_objet_craft", cr_objet_craftDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_objet_crafts -> get all the cr_objet_crafts.
     */
    @RequestMapping(value = "/cr_objet_crafts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_objet_craftDTO> getAllCr_objet_crafts() {
        log.debug("REST request to get all Cr_objet_crafts");
        return cr_objet_craftRepository.findAll().stream()
            .map(cr_objet_craftMapper::cr_objet_craftToCr_objet_craftDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_objet_crafts/:id -> get the "id" cr_objet_craft.
     */
    @RequestMapping(value = "/cr_objet_crafts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_objet_craftDTO> getCr_objet_craft(@PathVariable Long id) {
        log.debug("REST request to get Cr_objet_craft : {}", id);
        Cr_objet_craft cr_objet_craft = cr_objet_craftRepository.findOne(id);
        Cr_objet_craftDTO cr_objet_craftDTO = cr_objet_craftMapper.cr_objet_craftToCr_objet_craftDTO(cr_objet_craft);
        return Optional.ofNullable(cr_objet_craftDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_objet_crafts/:id -> delete the "id" cr_objet_craft.
     */
    @RequestMapping(value = "/cr_objet_crafts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_objet_craft(@PathVariable Long id) {
        log.debug("REST request to delete Cr_objet_craft : {}", id);
        cr_objet_craftRepository.delete(id);
        cr_objet_craftSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_objet_craft", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_objet_crafts/:query -> search for the cr_objet_craft corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_objet_crafts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_objet_craftDTO> searchCr_objet_crafts(@PathVariable String query) {
        log.debug("REST request to search Cr_objet_crafts for query {}", query);
        return StreamSupport
            .stream(cr_objet_craftSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_objet_craftMapper::cr_objet_craftToCr_objet_craftDTO)
            .collect(Collectors.toList());
    }
}
