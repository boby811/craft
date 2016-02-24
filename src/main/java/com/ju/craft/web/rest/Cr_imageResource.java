package com.ju.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.Cr_image;
import com.ju.craft.repository.Cr_imageRepository;
import com.ju.craft.repository.search.Cr_imageSearchRepository;
import com.ju.craft.web.rest.util.HeaderUtil;
import com.ju.craft.web.rest.dto.Cr_imageDTO;
import com.ju.craft.web.rest.mapper.Cr_imageMapper;
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
 * REST controller for managing Cr_image.
 */
@RestController
@RequestMapping("/api")
public class Cr_imageResource {

    private final Logger log = LoggerFactory.getLogger(Cr_imageResource.class);
        
    @Inject
    private Cr_imageRepository cr_imageRepository;
    
    @Inject
    private Cr_imageMapper cr_imageMapper;
    
    @Inject
    private Cr_imageSearchRepository cr_imageSearchRepository;
    
    /**
     * POST  /cr_images -> Create a new cr_image.
     */
    @RequestMapping(value = "/cr_images",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_imageDTO> createCr_image(@RequestBody Cr_imageDTO cr_imageDTO) throws URISyntaxException {
        log.debug("REST request to save Cr_image : {}", cr_imageDTO);
        if (cr_imageDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cr_image", "idexists", "A new cr_image cannot already have an ID")).body(null);
        }
        Cr_image cr_image = cr_imageMapper.cr_imageDTOToCr_image(cr_imageDTO);
        cr_image = cr_imageRepository.save(cr_image);
        Cr_imageDTO result = cr_imageMapper.cr_imageToCr_imageDTO(cr_image);
        cr_imageSearchRepository.save(cr_image);
        return ResponseEntity.created(new URI("/api/cr_images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cr_image", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cr_images -> Updates an existing cr_image.
     */
    @RequestMapping(value = "/cr_images",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_imageDTO> updateCr_image(@RequestBody Cr_imageDTO cr_imageDTO) throws URISyntaxException {
        log.debug("REST request to update Cr_image : {}", cr_imageDTO);
        if (cr_imageDTO.getId() == null) {
            return createCr_image(cr_imageDTO);
        }
        Cr_image cr_image = cr_imageMapper.cr_imageDTOToCr_image(cr_imageDTO);
        cr_image = cr_imageRepository.save(cr_image);
        Cr_imageDTO result = cr_imageMapper.cr_imageToCr_imageDTO(cr_image);
        cr_imageSearchRepository.save(cr_image);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cr_image", cr_imageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cr_images -> get all the cr_images.
     */
    @RequestMapping(value = "/cr_images",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Cr_imageDTO> getAllCr_images() {
        log.debug("REST request to get all Cr_images");
        return cr_imageRepository.findAll().stream()
            .map(cr_imageMapper::cr_imageToCr_imageDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /cr_images/:id -> get the "id" cr_image.
     */
    @RequestMapping(value = "/cr_images/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cr_imageDTO> getCr_image(@PathVariable Long id) {
        log.debug("REST request to get Cr_image : {}", id);
        Cr_image cr_image = cr_imageRepository.findOne(id);
        Cr_imageDTO cr_imageDTO = cr_imageMapper.cr_imageToCr_imageDTO(cr_image);
        return Optional.ofNullable(cr_imageDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cr_images/:id -> delete the "id" cr_image.
     */
    @RequestMapping(value = "/cr_images/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCr_image(@PathVariable Long id) {
        log.debug("REST request to delete Cr_image : {}", id);
        cr_imageRepository.delete(id);
        cr_imageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cr_image", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cr_images/:query -> search for the cr_image corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cr_images/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cr_imageDTO> searchCr_images(@PathVariable String query) {
        log.debug("REST request to search Cr_images for query {}", query);
        return StreamSupport
            .stream(cr_imageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cr_imageMapper::cr_imageToCr_imageDTO)
            .collect(Collectors.toList());
    }
}
