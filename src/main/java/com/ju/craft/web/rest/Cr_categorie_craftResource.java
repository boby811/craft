package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_categorie_craft;
import com.ju.craft.repository.Cr_categorie_craftRepository;
import com.ju.craft.repository.search.Cr_categorie_craftSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_categorie_craftDTO;
import com.ju.craft.web.rest.mapper.Cr_categorie_craftMapper;
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
 * REST controller for managing Cr_categorie_craft.
 */
@RestController
@RequestMapping("/api")
public class Cr_categorie_craftResource {

    private final Logger log = LoggerFactory.getLogger(Cr_categorie_craftResource.class);
        
    @Inject
    private Cr_categorie_craftRepository cr_categorie_craftRepository;
    
    @Inject
    private Cr_categorie_craftMapper cr_categorie_craftMapper;
    
    @Inject
    private Cr_categorie_craftSearchRepository cr_categorie_craftSearchRepository;
    
    /**
     * POST  /cr_categorie_crafts -> Create a new cr_categorie_craft.
     */
    @RequestMapping(value = "/cr_categorie_crafts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_categorie_craftDTO> createCr_categorie_craft(@Valid @RequestBody Cr_categorie_craftDTO cr_categorie_craftDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_categorie_craft : {}", cr_categorie_craftDTO);
        if (cr_categorie_craftDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_categorie_craft", "idexists", "A new cr_categorie_craft cannot already have an ID")).body(null);
        }
        Cr_categorie_craft cr_categorie_craft = cr_categorie_craftMapper.cr_categorie_craftDTOToCr_categorie_craft(cr_categorie_craftDTO);
        cr_categorie_craft = cr_categorie_craftRepository.save(cr_categorie_craft);
        Cr_categorie_craftDTO result = cr_categorie_craftMapper.cr_categorie_craftToCr_categorie_craftDTO(cr_categorie_craft);
        cr_categorie_craftSearchRepository.save(cr_categorie_craft);
        return ResponseEntity.created(new URI("/api/cr_categorie_crafts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_categorie_craft", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_categorie_crafts -> Updates an existing cr_categorie_craft.
     */
    @RequestMapping(value = "/cr_categorie_crafts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_categorie_craftDTO> updateCr_categorie_craft(@Valid @RequestBody Cr_categorie_craftDTO cr_categorie_craftDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_categorie_craft : {}", cr_categorie_craftDTO);
        if (cr_categorie_craftDTO.getId() == null) {
            return createCr_categorie_craft(cr_categorie_craftDTO);
        }
        Cr_categorie_craft cr_categorie_craft = cr_categorie_craftMapper.cr_categorie_craftDTOToCr_categorie_craft(cr_categorie_craftDTO);
        cr_categorie_craft = cr_categorie_craftRepository.save(cr_categorie_craft);
        Cr_categorie_craftDTO result = cr_categorie_craftMapper.cr_categorie_craftToCr_categorie_craftDTO(cr_categorie_craft);
        cr_categorie_craftSearchRepository.save(cr_categorie_craft);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_categorie_craft", cr_categorie_craftDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_categorie_crafts -> get all the cr_categorie_crafts.
     */
    @RequestMapping(value = "/cr_categorie_crafts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_categorie_craftDTO> getAllCr_categorie_crafts() {
        log.debug("REST request to get all Cr_categorie_crafts");
        return cr_categorie_craftRepository.findAllWithEagerRelationships().stream()
            .map(cr_categorie_craftMapper::cr_categorie_craftToCr_categorie_craftDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_categorie_crafts/:id -> get the "id" cr_categorie_craft.
     */
    @RequestMapping(value = "/cr_categorie_crafts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_categorie_craftDTO> getCr_categorie_craft(@PathVariable Long id) {
        log.debug("REST request to get Cr_categorie_craft : {}", id);
        Cr_categorie_craft cr_categorie_craft = cr_categorie_craftRepository.findOneWithEagerRelationships(id);
        Cr_categorie_craftDTO cr_categorie_craftDTO = cr_categorie_craftMapper.cr_categorie_craftToCr_categorie_craftDTO(cr_categorie_craft);
        return Optional.ofNullable(cr_categorie_craftDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_categorie_crafts/:id -> delete the "id" cr_categorie_craft.
     */
    @RequestMapping(value = "/cr_categorie_crafts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_categorie_craft(@PathVariable Long id) {
        log.debug("REST request to delete Cr_categorie_craft : {}", id);
        cr_categorie_craftRepository.delete(id);
        cr_categorie_craftSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_categorie_craft", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_categorie_crafts/:query -> search for the cr_categorie_craft corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_categorie_crafts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_categorie_craftDTO> searchCr_categorie_crafts(@PathVariable String query) {
        log.debug("REST request to search Cr_categorie_crafts for query {}", query);
        return StreamSupport
            .stream(cr_categorie_craftSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_categorie_craftMapper::cr_categorie_craftToCr_categorie_craftDTO)
            .collect(Collectors.toList());
    }
}
