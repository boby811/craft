package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_categorie_craft;
import com.ju.craft.repository.Cr_categorie_craftRepository;
import com.ju.craft.repository.search.Cr_categorie_craftSearchRepository;
import com.ju.craft.web.rest.dto.Cr_categorie_craftDTO;
import com.ju.craft.web.rest.mapper.Cr_categorie_craftMapper;

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
 * Test class for the Cr_categorie_craftResource REST controller.
 *
 * @see Cr_categorie_craftResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_categorie_craftResourceIntTest {

    private static final String DEFAULT_CC_NOM_COURT_FR_FR = "AAAAA";
    private static final String UPDATED_CC_NOM_COURT_FR_FR = "BBBBB";
    private static final String DEFAULT_CC_NOM_LONG_FR_FR = "AAAAA";
    private static final String UPDATED_CC_NOM_LONG_FR_FR = "BBBBB";

    @Inject
    private Cr_categorie_craftRepository cr_categorie_craftRepository;

    @Inject
    private Cr_categorie_craftMapper cr_categorie_craftMapper;

    @Inject
    private Cr_categorie_craftSearchRepository cr_categorie_craftSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_categorie_craftMockMvc;

    private Cr_categorie_craft cr_categorie_craft;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_categorie_craftResource cr_categorie_craftResource = new Cr_categorie_craftResource();
        ReflectionTestUtils.setField(cr_categorie_craftResource, "cr_categorie_craftSearchRepository", cr_categorie_craftSearchRepository);
        ReflectionTestUtils.setField(cr_categorie_craftResource, "cr_categorie_craftRepository", cr_categorie_craftRepository);
        ReflectionTestUtils.setField(cr_categorie_craftResource, "cr_categorie_craftMapper", cr_categorie_craftMapper);
        this.restCr_categorie_craftMockMvc = MockMvcBuilders.standaloneSetup(cr_categorie_craftResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_categorie_craft = new Cr_categorie_craft();
        cr_categorie_craft.setCc_nom_court_fr_fr(DEFAULT_CC_NOM_COURT_FR_FR);
        cr_categorie_craft.setCc_nom_long_fr_fr(DEFAULT_CC_NOM_LONG_FR_FR);
    }

    @Test
    @Transactional
    public void createCr_categorie_craft() throws Exception {
        int databaseSizeBeforeCreate = cr_categorie_craftRepository.findAll().size();

        // Create the Cr_categorie_craft
        Cr_categorie_craftDTO cr_categorie_craftDTO = cr_categorie_craftMapper.cr_categorie_craftToCr_categorie_craftDTO(cr_categorie_craft);

        restCr_categorie_craftMockMvc.perform(post("/api/cr_categorie_crafts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_categorie_craftDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_categorie_craft in the database
        List<Cr_categorie_craft> cr_categorie_crafts = cr_categorie_craftRepository.findAll();
        assertThat(cr_categorie_crafts).hasSize(databaseSizeBeforeCreate + 1);
        Cr_categorie_craft testCr_categorie_craft = cr_categorie_crafts.get(cr_categorie_crafts.size() - 1);
        assertThat(testCr_categorie_craft.getCc_nom_court_fr_fr()).isEqualTo(DEFAULT_CC_NOM_COURT_FR_FR);
        assertThat(testCr_categorie_craft.getCc_nom_long_fr_fr()).isEqualTo(DEFAULT_CC_NOM_LONG_FR_FR);
    }

    @Test
    @Transactional
    public void checkCc_nom_long_fr_frIsRequired() throws Exception {
        int databaseSizeBeforeTest = cr_categorie_craftRepository.findAll().size();
        // set the field null
        cr_categorie_craft.setCc_nom_long_fr_fr(null);

        // Create the Cr_categorie_craft, which fails.
        Cr_categorie_craftDTO cr_categorie_craftDTO = cr_categorie_craftMapper.cr_categorie_craftToCr_categorie_craftDTO(cr_categorie_craft);

        restCr_categorie_craftMockMvc.perform(post("/api/cr_categorie_crafts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_categorie_craftDTO)))
                .andExpect(status().isBadRequest());

        List<Cr_categorie_craft> cr_categorie_crafts = cr_categorie_craftRepository.findAll();
        assertThat(cr_categorie_crafts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCr_categorie_crafts() throws Exception {
        // Initialize the database
        cr_categorie_craftRepository.saveAndFlush(cr_categorie_craft);

        // Get all the cr_categorie_crafts
        restCr_categorie_craftMockMvc.perform(get("/api/cr_categorie_crafts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_categorie_craft.getId().intValue())))
                .andExpect(jsonPath("$.[*].cc_nom_court_fr_fr").value(hasItem(DEFAULT_CC_NOM_COURT_FR_FR.toString())))
                .andExpect(jsonPath("$.[*].cc_nom_long_fr_fr").value(hasItem(DEFAULT_CC_NOM_LONG_FR_FR.toString())));
    }

    @Test
    @Transactional
    public void getCr_categorie_craft() throws Exception {
        // Initialize the database
        cr_categorie_craftRepository.saveAndFlush(cr_categorie_craft);

        // Get the cr_categorie_craft
        restCr_categorie_craftMockMvc.perform(get("/api/cr_categorie_crafts/{id}", cr_categorie_craft.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_categorie_craft.getId().intValue()))
            .andExpect(jsonPath("$.cc_nom_court_fr_fr").value(DEFAULT_CC_NOM_COURT_FR_FR.toString()))
            .andExpect(jsonPath("$.cc_nom_long_fr_fr").value(DEFAULT_CC_NOM_LONG_FR_FR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_categorie_craft() throws Exception {
        // Get the cr_categorie_craft
        restCr_categorie_craftMockMvc.perform(get("/api/cr_categorie_crafts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_categorie_craft() throws Exception {
        // Initialize the database
        cr_categorie_craftRepository.saveAndFlush(cr_categorie_craft);

		int databaseSizeBeforeUpdate = cr_categorie_craftRepository.findAll().size();

        // Update the cr_categorie_craft
        cr_categorie_craft.setCc_nom_court_fr_fr(UPDATED_CC_NOM_COURT_FR_FR);
        cr_categorie_craft.setCc_nom_long_fr_fr(UPDATED_CC_NOM_LONG_FR_FR);
        Cr_categorie_craftDTO cr_categorie_craftDTO = cr_categorie_craftMapper.cr_categorie_craftToCr_categorie_craftDTO(cr_categorie_craft);

        restCr_categorie_craftMockMvc.perform(put("/api/cr_categorie_crafts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_categorie_craftDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_categorie_craft in the database
        List<Cr_categorie_craft> cr_categorie_crafts = cr_categorie_craftRepository.findAll();
        assertThat(cr_categorie_crafts).hasSize(databaseSizeBeforeUpdate);
        Cr_categorie_craft testCr_categorie_craft = cr_categorie_crafts.get(cr_categorie_crafts.size() - 1);
        assertThat(testCr_categorie_craft.getCc_nom_court_fr_fr()).isEqualTo(UPDATED_CC_NOM_COURT_FR_FR);
        assertThat(testCr_categorie_craft.getCc_nom_long_fr_fr()).isEqualTo(UPDATED_CC_NOM_LONG_FR_FR);
    }

    @Test
    @Transactional
    public void deleteCr_categorie_craft() throws Exception {
        // Initialize the database
        cr_categorie_craftRepository.saveAndFlush(cr_categorie_craft);

		int databaseSizeBeforeDelete = cr_categorie_craftRepository.findAll().size();

        // Get the cr_categorie_craft
        restCr_categorie_craftMockMvc.perform(delete("/api/cr_categorie_crafts/{id}", cr_categorie_craft.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_categorie_craft> cr_categorie_crafts = cr_categorie_craftRepository.findAll();
        assertThat(cr_categorie_crafts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
