package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_rarete;
import com.ju.craft.repository.Cr_rareteRepository;
import com.ju.craft.repository.search.Cr_rareteSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_rareteDTO;
import com.ju.craft.web.rest.mapper.Cr_rareteMapper;
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
 * REST controller for managing Cr_rarete.
 */
@RestController
@RequestMapping("/api")
public class Cr_rareteResource {

    private final Logger log = LoggerFactory.getLogger(Cr_rareteResource.class);
        
    @Inject
    private Cr_rareteRepository cr_rareteRepository;
    
    @Inject
    private Cr_rareteMapper cr_rareteMapper;
    
    @Inject
    private Cr_rareteSearchRepository cr_rareteSearchRepository;
    
    /**
     * POST  /cr_raretes -> Create a new cr_rarete.
     */
    @RequestMapping(value = "/cr_raretes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_rareteDTO> createCr_rarete(@Valid @RequestBody Cr_rareteDTO cr_rareteDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_rarete : {}", cr_rareteDTO);
        if (cr_rareteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_rarete", "idexists", "A new cr_rarete cannot already have an ID")).body(null);
        }
        Cr_rarete cr_rarete = cr_rareteMapper.cr_rareteDTOToCr_rarete(cr_rareteDTO);
        cr_rarete = cr_rareteRepository.save(cr_rarete);
        Cr_rareteDTO result = cr_rareteMapper.cr_rareteToCr_rareteDTO(cr_rarete);
        cr_rareteSearchRepository.save(cr_rarete);
        return ResponseEntity.created(new URI("/api/cr_raretes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_rarete", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_raretes -> Updates an existing cr_rarete.
     */
    @RequestMapping(value = "/cr_raretes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_rareteDTO> updateCr_rarete(@Valid @RequestBody Cr_rareteDTO cr_rareteDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_rarete : {}", cr_rareteDTO);
        if (cr_rareteDTO.getId() == null) {
            return createCr_rarete(cr_rareteDTO);
        }
        Cr_rarete cr_rarete = cr_rareteMapper.cr_rareteDTOToCr_rarete(cr_rareteDTO);
        cr_rarete = cr_rareteRepository.save(cr_rarete);
        Cr_rareteDTO result = cr_rareteMapper.cr_rareteToCr_rareteDTO(cr_rarete);
        cr_rareteSearchRepository.save(cr_rarete);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_rarete", cr_rareteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_raretes -> get all the cr_raretes.
     */
    @RequestMapping(value = "/cr_raretes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_rareteDTO> getAllCr_raretes() {
        log.debug("REST request to get all Cr_raretes");
        return cr_rareteRepository.findAll().stream()
            .map(cr_rareteMapper::cr_rareteToCr_rareteDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_raretes/:id -> get the "id" cr_rarete.
     */
    @RequestMapping(value = "/cr_raretes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_rareteDTO> getCr_rarete(@PathVariable Long id) {
        log.debug("REST request to get Cr_rarete : {}", id);
        Cr_rarete cr_rarete = cr_rareteRepository.findOne(id);
        Cr_rareteDTO cr_rareteDTO = cr_rareteMapper.cr_rareteToCr_rareteDTO(cr_rarete);
        return Optional.ofNullable(cr_rareteDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_raretes/:id -> delete the "id" cr_rarete.
     */
    @RequestMapping(value = "/cr_raretes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_rarete(@PathVariable Long id) {
        log.debug("REST request to delete Cr_rarete : {}", id);
        cr_rareteRepository.delete(id);
        cr_rareteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_rarete", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_raretes/:query -> search for the cr_rarete corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_raretes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_rareteDTO> searchCr_raretes(@PathVariable String query) {
        log.debug("REST request to search Cr_raretes for query {}", query);
        return StreamSupport
            .stream(cr_rareteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_rareteMapper::cr_rareteToCr_rareteDTO)
            .collect(Collectors.toList());
    }
}
