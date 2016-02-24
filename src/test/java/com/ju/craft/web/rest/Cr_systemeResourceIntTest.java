package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_systeme;
import com.ju.craft.repository.Cr_systemeRepository;
import com.ju.craft.repository.search.Cr_systemeSearchRepository;

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
 * Test class for the Cr_systemeResource REST controller.
 *
 * @see Cr_systemeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_systemeResourceIntTest {

    private static final String DEFAULT_SY_NOM_FR_FR = "AAAAA";
    private static final String UPDATED_SY_NOM_FR_FR = "BBBBB";

    @Inject
    private Cr_systemeRepository cr_systemeRepository;

    @Inject
    private Cr_systemeSearchRepository cr_systemeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_systemeMockMvc;

    private Cr_systeme cr_systeme;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_systemeResource cr_systemeResource = new Cr_systemeResource();
        ReflectionTestUtils.setField(cr_systemeResource, "cr_systemeSearchRepository", cr_systemeSearchRepository);
        ReflectionTestUtils.setField(cr_systemeResource, "cr_systemeRepository", cr_systemeRepository);
        this.restCr_systemeMockMvc = MockMvcBuilders.standaloneSetup(cr_systemeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_systeme = new Cr_systeme();
        cr_systeme.setSy_nom_fr_fr(DEFAULT_SY_NOM_FR_FR);
    }

    @Test
    @Transactional
    public void createCr_systeme() throws Exception {
        int databaseSizeBeforeCreate = cr_systemeRepository.findAll().size();

        // Create the Cr_systeme

        restCr_systemeMockMvc.perform(post("/api/cr_systemes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_systeme)))
                .andExpect(status().isCreated());

        // Validate the Cr_systeme in the database
        List<Cr_systeme> cr_systemes = cr_systemeRepository.findAll();
        assertThat(cr_systemes).hasSize(databaseSizeBeforeCreate + 1);
        Cr_systeme testCr_systeme = cr_systemes.get(cr_systemes.size() - 1);
        assertThat(testCr_systeme.getSy_nom_fr_fr()).isEqualTo(DEFAULT_SY_NOM_FR_FR);
    }

    @Test
    @Transactional
    public void checkSy_nom_fr_frIsRequired() throws Exception {
        int databaseSizeBeforeTest = cr_systemeRepository.findAll().size();
        // set the field null
        cr_systeme.setSy_nom_fr_fr(null);

        // Create the Cr_systeme, which fails.

        restCr_systemeMockMvc.perform(post("/api/cr_systemes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_systeme)))
                .andExpect(status().isBadRequest());

        List<Cr_systeme> cr_systemes = cr_systemeRepository.findAll();
        assertThat(cr_systemes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCr_systemes() throws Exception {
        // Initialize the database
        cr_systemeRepository.saveAndFlush(cr_systeme);

        // Get all the cr_systemes
        restCr_systemeMockMvc.perform(get("/api/cr_systemes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_systeme.getId().intValue())))
                .andExpect(jsonPath("$.[*].sy_nom_fr_fr").value(hasItem(DEFAULT_SY_NOM_FR_FR.toString())));
    }

    @Test
    @Transactional
    public void getCr_systeme() throws Exception {
        // Initialize the database
        cr_systemeRepository.saveAndFlush(cr_systeme);

        // Get the cr_systeme
        restCr_systemeMockMvc.perform(get("/api/cr_systemes/{id}", cr_systeme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_systeme.getId().intValue()))
            .andExpect(jsonPath("$.sy_nom_fr_fr").value(DEFAULT_SY_NOM_FR_FR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_systeme() throws Exception {
        // Get the cr_systeme
        restCr_systemeMockMvc.perform(get("/api/cr_systemes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_systeme() throws Exception {
        // Initialize the database
        cr_systemeRepository.saveAndFlush(cr_systeme);

		int databaseSizeBeforeUpdate = cr_systemeRepository.findAll().size();

        // Update the cr_systeme
        cr_systeme.setSy_nom_fr_fr(UPDATED_SY_NOM_FR_FR);

        restCr_systemeMockMvc.perform(put("/api/cr_systemes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_systeme)))
                .andExpect(status().isOk());

        // Validate the Cr_systeme in the database
        List<Cr_systeme> cr_systemes = cr_systemeRepository.findAll();
        assertThat(cr_systemes).hasSize(databaseSizeBeforeUpdate);
        Cr_systeme testCr_systeme = cr_systemes.get(cr_systemes.size() - 1);
        assertThat(testCr_systeme.getSy_nom_fr_fr()).isEqualTo(UPDATED_SY_NOM_FR_FR);
    }

    @Test
    @Transactional
    public void deleteCr_systeme() throws Exception {
        // Initialize the database
        cr_systemeRepository.saveAndFlush(cr_systeme);

		int databaseSizeBeforeDelete = cr_systemeRepository.findAll().size();

        // Get the cr_systeme
        restCr_systemeMockMvc.perform(delete("/api/cr_systemes/{id}", cr_systeme.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_systeme> cr_systemes = cr_systemeRepository.findAll();
        assertThat(cr_systemes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
