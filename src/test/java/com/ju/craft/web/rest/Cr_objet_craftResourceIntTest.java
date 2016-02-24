package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_objet_craft;
import com.ju.craft.repository.Cr_objet_craftRepository;
import com.ju.craft.repository.search.Cr_objet_craftSearchRepository;
import com.ju.craft.web.rest.dto.Cr_objet_craftDTO;
import com.ju.craft.web.rest.mapper.Cr_objet_craftMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Cr_objet_craftResource REST controller.
 *
 * @see Cr_objet_craftResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_objet_craftResourceIntTest {


    private static final Integer DEFAULT_OC_QUANTITE_ELEMENT = 1;
    private static final Integer UPDATED_OC_QUANTITE_ELEMENT = 2;

    @Inject
    private Cr_objet_craftRepository cr_objet_craftRepository;

    @Inject
    private Cr_objet_craftMapper cr_objet_craftMapper;

    @Inject
    private Cr_objet_craftSearchRepository cr_objet_craftSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_objet_craftMockMvc;

    private Cr_objet_craft cr_objet_craft;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_objet_craftResource cr_objet_craftResource = new Cr_objet_craftResource();
        ReflectionTestUtils.setField(cr_objet_craftResource, "cr_objet_craftSearchRepository", cr_objet_craftSearchRepository);
        ReflectionTestUtils.setField(cr_objet_craftResource, "cr_objet_craftRepository", cr_objet_craftRepository);
        ReflectionTestUtils.setField(cr_objet_craftResource, "cr_objet_craftMapper", cr_objet_craftMapper);
        this.restCr_objet_craftMockMvc = MockMvcBuilders.standaloneSetup(cr_objet_craftResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_objet_craft = new Cr_objet_craft();
        cr_objet_craft.setOc_quantite_element(DEFAULT_OC_QUANTITE_ELEMENT);
    }

    @Test
    @Transactional
    public void createCr_objet_craft() throws Exception {
        int databaseSizeBeforeCreate = cr_objet_craftRepository.findAll().size();

        // Create the Cr_objet_craft
        Cr_objet_craftDTO cr_objet_craftDTO = cr_objet_craftMapper.cr_objet_craftToCr_objet_craftDTO(cr_objet_craft);

        restCr_objet_craftMockMvc.perform(post("/api/cr_objet_crafts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_objet_craftDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_objet_craft in the database
        List<Cr_objet_craft> cr_objet_crafts = cr_objet_craftRepository.findAll();
        assertThat(cr_objet_crafts).hasSize(databaseSizeBeforeCreate + 1);
        Cr_objet_craft testCr_objet_craft = cr_objet_crafts.get(cr_objet_crafts.size() - 1);
        assertThat(testCr_objet_craft.getOc_quantite_element()).isEqualTo(DEFAULT_OC_QUANTITE_ELEMENT);
    }

    @Test
    @Transactional
    public void checkOc_quantite_elementIsRequired() throws Exception {
        int databaseSizeBeforeTest = cr_objet_craftRepository.findAll().size();
        // set the field null
        cr_objet_craft.setOc_quantite_element(null);

        // Create the Cr_objet_craft, which fails.
        Cr_objet_craftDTO cr_objet_craftDTO = cr_objet_craftMapper.cr_objet_craftToCr_objet_craftDTO(cr_objet_craft);

        restCr_objet_craftMockMvc.perform(post("/api/cr_objet_crafts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_objet_craftDTO)))
                .andExpect(status().isBadRequest());

        List<Cr_objet_craft> cr_objet_crafts = cr_objet_craftRepository.findAll();
        assertThat(cr_objet_crafts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCr_objet_crafts() throws Exception {
        // Initialize the database
        cr_objet_craftRepository.saveAndFlush(cr_objet_craft);

        // Get all the cr_objet_crafts
        restCr_objet_craftMockMvc.perform(get("/api/cr_objet_crafts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_objet_craft.getId().intValue())))
                .andExpect(jsonPath("$.[*].oc_quantite_element").value(hasItem(DEFAULT_OC_QUANTITE_ELEMENT)));
    }

    @Test
    @Transactional
    public void getCr_objet_craft() throws Exception {
        // Initialize the database
        cr_objet_craftRepository.saveAndFlush(cr_objet_craft);

        // Get the cr_objet_craft
        restCr_objet_craftMockMvc.perform(get("/api/cr_objet_crafts/{id}", cr_objet_craft.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_objet_craft.getId().intValue()))
            .andExpect(jsonPath("$.oc_quantite_element").value(DEFAULT_OC_QUANTITE_ELEMENT));
    }

    @Test
    @Transactional
    public void getNonExistingCr_objet_craft() throws Exception {
        // Get the cr_objet_craft
        restCr_objet_craftMockMvc.perform(get("/api/cr_objet_crafts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_objet_craft() throws Exception {
        // Initialize the database
        cr_objet_craftRepository.saveAndFlush(cr_objet_craft);

		int databaseSizeBeforeUpdate = cr_objet_craftRepository.findAll().size();

        // Update the cr_objet_craft
        cr_objet_craft.setOc_quantite_element(UPDATED_OC_QUANTITE_ELEMENT);
        Cr_objet_craftDTO cr_objet_craftDTO = cr_objet_craftMapper.cr_objet_craftToCr_objet_craftDTO(cr_objet_craft);

        restCr_objet_craftMockMvc.perform(put("/api/cr_objet_crafts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_objet_craftDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_objet_craft in the database
        List<Cr_objet_craft> cr_objet_crafts = cr_objet_craftRepository.findAll();
        assertThat(cr_objet_crafts).hasSize(databaseSizeBeforeUpdate);
        Cr_objet_craft testCr_objet_craft = cr_objet_crafts.get(cr_objet_crafts.size() - 1);
        assertThat(testCr_objet_craft.getOc_quantite_element()).isEqualTo(UPDATED_OC_QUANTITE_ELEMENT);
    }

    @Test
    @Transactional
    public void deleteCr_objet_craft() throws Exception {
        // Initialize the database
        cr_objet_craftRepository.saveAndFlush(cr_objet_craft);

		int databaseSizeBeforeDelete = cr_objet_craftRepository.findAll().size();

        // Get the cr_objet_craft
        restCr_objet_craftMockMvc.perform(delete("/api/cr_objet_crafts/{id}", cr_objet_craft.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_objet_craft> cr_objet_crafts = cr_objet_craftRepository.findAll();
        assertThat(cr_objet_crafts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
