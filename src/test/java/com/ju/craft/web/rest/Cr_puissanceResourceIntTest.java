package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_puissance;
import com.ju.craft.repository.Cr_puissanceRepository;
import com.ju.craft.repository.search.Cr_puissanceSearchRepository;
import com.ju.craft.web.rest.dto.Cr_puissanceDTO;
import com.ju.craft.web.rest.mapper.Cr_puissanceMapper;

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
 * Test class for the Cr_puissanceResource REST controller.
 *
 * @see Cr_puissanceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_puissanceResourceIntTest {

    private static final String DEFAULT_PU_LIBELLE = "AAAAA";
    private static final String UPDATED_PU_LIBELLE = "BBBBB";

    @Inject
    private Cr_puissanceRepository cr_puissanceRepository;

    @Inject
    private Cr_puissanceMapper cr_puissanceMapper;

    @Inject
    private Cr_puissanceSearchRepository cr_puissanceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_puissanceMockMvc;

    private Cr_puissance cr_puissance;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_puissanceResource cr_puissanceResource = new Cr_puissanceResource();
        ReflectionTestUtils.setField(cr_puissanceResource, "cr_puissanceSearchRepository", cr_puissanceSearchRepository);
        ReflectionTestUtils.setField(cr_puissanceResource, "cr_puissanceRepository", cr_puissanceRepository);
        ReflectionTestUtils.setField(cr_puissanceResource, "cr_puissanceMapper", cr_puissanceMapper);
        this.restCr_puissanceMockMvc = MockMvcBuilders.standaloneSetup(cr_puissanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_puissance = new Cr_puissance();
        cr_puissance.setPu_libelle(DEFAULT_PU_LIBELLE);
    }

    @Test
    @Transactional
    public void createCr_puissance() throws Exception {
        int databaseSizeBeforeCreate = cr_puissanceRepository.findAll().size();

        // Create the Cr_puissance
        Cr_puissanceDTO cr_puissanceDTO = cr_puissanceMapper.cr_puissanceToCr_puissanceDTO(cr_puissance);

        restCr_puissanceMockMvc.perform(post("/api/cr_puissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_puissanceDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_puissance in the database
        List<Cr_puissance> cr_puissances = cr_puissanceRepository.findAll();
        assertThat(cr_puissances).hasSize(databaseSizeBeforeCreate + 1);
        Cr_puissance testCr_puissance = cr_puissances.get(cr_puissances.size() - 1);
        assertThat(testCr_puissance.getPu_libelle()).isEqualTo(DEFAULT_PU_LIBELLE);
    }

    @Test
    @Transactional
    public void checkPu_libelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cr_puissanceRepository.findAll().size();
        // set the field null
        cr_puissance.setPu_libelle(null);

        // Create the Cr_puissance, which fails.
        Cr_puissanceDTO cr_puissanceDTO = cr_puissanceMapper.cr_puissanceToCr_puissanceDTO(cr_puissance);

        restCr_puissanceMockMvc.perform(post("/api/cr_puissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_puissanceDTO)))
                .andExpect(status().isBadRequest());

        List<Cr_puissance> cr_puissances = cr_puissanceRepository.findAll();
        assertThat(cr_puissances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCr_puissances() throws Exception {
        // Initialize the database
        cr_puissanceRepository.saveAndFlush(cr_puissance);

        // Get all the cr_puissances
        restCr_puissanceMockMvc.perform(get("/api/cr_puissances?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_puissance.getId().intValue())))
                .andExpect(jsonPath("$.[*].pu_libelle").value(hasItem(DEFAULT_PU_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getCr_puissance() throws Exception {
        // Initialize the database
        cr_puissanceRepository.saveAndFlush(cr_puissance);

        // Get the cr_puissance
        restCr_puissanceMockMvc.perform(get("/api/cr_puissances/{id}", cr_puissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_puissance.getId().intValue()))
            .andExpect(jsonPath("$.pu_libelle").value(DEFAULT_PU_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_puissance() throws Exception {
        // Get the cr_puissance
        restCr_puissanceMockMvc.perform(get("/api/cr_puissances/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_puissance() throws Exception {
        // Initialize the database
        cr_puissanceRepository.saveAndFlush(cr_puissance);

		int databaseSizeBeforeUpdate = cr_puissanceRepository.findAll().size();

        // Update the cr_puissance
        cr_puissance.setPu_libelle(UPDATED_PU_LIBELLE);
        Cr_puissanceDTO cr_puissanceDTO = cr_puissanceMapper.cr_puissanceToCr_puissanceDTO(cr_puissance);

        restCr_puissanceMockMvc.perform(put("/api/cr_puissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_puissanceDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_puissance in the database
        List<Cr_puissance> cr_puissances = cr_puissanceRepository.findAll();
        assertThat(cr_puissances).hasSize(databaseSizeBeforeUpdate);
        Cr_puissance testCr_puissance = cr_puissances.get(cr_puissances.size() - 1);
        assertThat(testCr_puissance.getPu_libelle()).isEqualTo(UPDATED_PU_LIBELLE);
    }

    @Test
    @Transactional
    public void deleteCr_puissance() throws Exception {
        // Initialize the database
        cr_puissanceRepository.saveAndFlush(cr_puissance);

		int databaseSizeBeforeDelete = cr_puissanceRepository.findAll().size();

        // Get the cr_puissance
        restCr_puissanceMockMvc.perform(delete("/api/cr_puissances/{id}", cr_puissance.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_puissance> cr_puissances = cr_puissanceRepository.findAll();
        assertThat(cr_puissances).hasSize(databaseSizeBeforeDelete - 1);
    }
}
