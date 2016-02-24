package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_rarete;
import com.ju.craft.repository.Cr_rareteRepository;
import com.ju.craft.repository.search.Cr_rareteSearchRepository;
import com.ju.craft.web.rest.dto.Cr_rareteDTO;
import com.ju.craft.web.rest.mapper.Cr_rareteMapper;

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
 * Test class for the Cr_rareteResource REST controller.
 *
 * @see Cr_rareteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_rareteResourceIntTest {

    private static final String DEFAULT_RA_LIBELLE_FR_FR = "AAAAA";
    private static final String UPDATED_RA_LIBELLE_FR_FR = "BBBBB";

    @Inject
    private Cr_rareteRepository cr_rareteRepository;

    @Inject
    private Cr_rareteMapper cr_rareteMapper;

    @Inject
    private Cr_rareteSearchRepository cr_rareteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_rareteMockMvc;

    private Cr_rarete cr_rarete;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_rareteResource cr_rareteResource = new Cr_rareteResource();
        ReflectionTestUtils.setField(cr_rareteResource, "cr_rareteSearchRepository", cr_rareteSearchRepository);
        ReflectionTestUtils.setField(cr_rareteResource, "cr_rareteRepository", cr_rareteRepository);
        ReflectionTestUtils.setField(cr_rareteResource, "cr_rareteMapper", cr_rareteMapper);
        this.restCr_rareteMockMvc = MockMvcBuilders.standaloneSetup(cr_rareteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_rarete = new Cr_rarete();
        cr_rarete.setRa_libelle_fr_fr(DEFAULT_RA_LIBELLE_FR_FR);
    }

    @Test
    @Transactional
    public void createCr_rarete() throws Exception {
        int databaseSizeBeforeCreate = cr_rareteRepository.findAll().size();

        // Create the Cr_rarete
        Cr_rareteDTO cr_rareteDTO = cr_rareteMapper.cr_rareteToCr_rareteDTO(cr_rarete);

        restCr_rareteMockMvc.perform(post("/api/cr_raretes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_rareteDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_rarete in the database
        List<Cr_rarete> cr_raretes = cr_rareteRepository.findAll();
        assertThat(cr_raretes).hasSize(databaseSizeBeforeCreate + 1);
        Cr_rarete testCr_rarete = cr_raretes.get(cr_raretes.size() - 1);
        assertThat(testCr_rarete.getRa_libelle_fr_fr()).isEqualTo(DEFAULT_RA_LIBELLE_FR_FR);
    }

    @Test
    @Transactional
    public void checkRa_libelle_fr_frIsRequired() throws Exception {
        int databaseSizeBeforeTest = cr_rareteRepository.findAll().size();
        // set the field null
        cr_rarete.setRa_libelle_fr_fr(null);

        // Create the Cr_rarete, which fails.
        Cr_rareteDTO cr_rareteDTO = cr_rareteMapper.cr_rareteToCr_rareteDTO(cr_rarete);

        restCr_rareteMockMvc.perform(post("/api/cr_raretes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_rareteDTO)))
                .andExpect(status().isBadRequest());

        List<Cr_rarete> cr_raretes = cr_rareteRepository.findAll();
        assertThat(cr_raretes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCr_raretes() throws Exception {
        // Initialize the database
        cr_rareteRepository.saveAndFlush(cr_rarete);

        // Get all the cr_raretes
        restCr_rareteMockMvc.perform(get("/api/cr_raretes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_rarete.getId().intValue())))
                .andExpect(jsonPath("$.[*].ra_libelle_fr_fr").value(hasItem(DEFAULT_RA_LIBELLE_FR_FR.toString())));
    }

    @Test
    @Transactional
    public void getCr_rarete() throws Exception {
        // Initialize the database
        cr_rareteRepository.saveAndFlush(cr_rarete);

        // Get the cr_rarete
        restCr_rareteMockMvc.perform(get("/api/cr_raretes/{id}", cr_rarete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_rarete.getId().intValue()))
            .andExpect(jsonPath("$.ra_libelle_fr_fr").value(DEFAULT_RA_LIBELLE_FR_FR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_rarete() throws Exception {
        // Get the cr_rarete
        restCr_rareteMockMvc.perform(get("/api/cr_raretes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_rarete() throws Exception {
        // Initialize the database
        cr_rareteRepository.saveAndFlush(cr_rarete);

		int databaseSizeBeforeUpdate = cr_rareteRepository.findAll().size();

        // Update the cr_rarete
        cr_rarete.setRa_libelle_fr_fr(UPDATED_RA_LIBELLE_FR_FR);
        Cr_rareteDTO cr_rareteDTO = cr_rareteMapper.cr_rareteToCr_rareteDTO(cr_rarete);

        restCr_rareteMockMvc.perform(put("/api/cr_raretes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_rareteDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_rarete in the database
        List<Cr_rarete> cr_raretes = cr_rareteRepository.findAll();
        assertThat(cr_raretes).hasSize(databaseSizeBeforeUpdate);
        Cr_rarete testCr_rarete = cr_raretes.get(cr_raretes.size() - 1);
        assertThat(testCr_rarete.getRa_libelle_fr_fr()).isEqualTo(UPDATED_RA_LIBELLE_FR_FR);
    }

    @Test
    @Transactional
    public void deleteCr_rarete() throws Exception {
        // Initialize the database
        cr_rareteRepository.saveAndFlush(cr_rarete);

		int databaseSizeBeforeDelete = cr_rareteRepository.findAll().size();

        // Get the cr_rarete
        restCr_rareteMockMvc.perform(delete("/api/cr_raretes/{id}", cr_rarete.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_rarete> cr_raretes = cr_rareteRepository.findAll();
        assertThat(cr_raretes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
