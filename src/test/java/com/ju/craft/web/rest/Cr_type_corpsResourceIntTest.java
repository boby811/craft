package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_type_corps;
import com.ju.craft.repository.Cr_type_corpsRepository;
import com.ju.craft.repository.search.Cr_type_corpsSearchRepository;
import com.ju.craft.web.rest.dto.Cr_type_corpsDTO;
import com.ju.craft.web.rest.mapper.Cr_type_corpsMapper;

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
 * Test class for the Cr_type_corpsResource REST controller.
 *
 * @see Cr_type_corpsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_type_corpsResourceIntTest {

    private static final String DEFAULT_TC_LIBELLE_FR_FR = "AAAAA";
    private static final String UPDATED_TC_LIBELLE_FR_FR = "BBBBB";

    @Inject
    private Cr_type_corpsRepository cr_type_corpsRepository;

    @Inject
    private Cr_type_corpsMapper cr_type_corpsMapper;

    @Inject
    private Cr_type_corpsSearchRepository cr_type_corpsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_type_corpsMockMvc;

    private Cr_type_corps cr_type_corps;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_type_corpsResource cr_type_corpsResource = new Cr_type_corpsResource();
        ReflectionTestUtils.setField(cr_type_corpsResource, "cr_type_corpsSearchRepository", cr_type_corpsSearchRepository);
        ReflectionTestUtils.setField(cr_type_corpsResource, "cr_type_corpsRepository", cr_type_corpsRepository);
        ReflectionTestUtils.setField(cr_type_corpsResource, "cr_type_corpsMapper", cr_type_corpsMapper);
        this.restCr_type_corpsMockMvc = MockMvcBuilders.standaloneSetup(cr_type_corpsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_type_corps = new Cr_type_corps();
        cr_type_corps.setTc_libelle_fr_fr(DEFAULT_TC_LIBELLE_FR_FR);
    }

    @Test
    @Transactional
    public void createCr_type_corps() throws Exception {
        int databaseSizeBeforeCreate = cr_type_corpsRepository.findAll().size();

        // Create the Cr_type_corps
        Cr_type_corpsDTO cr_type_corpsDTO = cr_type_corpsMapper.cr_type_corpsToCr_type_corpsDTO(cr_type_corps);

        restCr_type_corpsMockMvc.perform(post("/api/cr_type_corpss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_type_corpsDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_type_corps in the database
        List<Cr_type_corps> cr_type_corpss = cr_type_corpsRepository.findAll();
        assertThat(cr_type_corpss).hasSize(databaseSizeBeforeCreate + 1);
        Cr_type_corps testCr_type_corps = cr_type_corpss.get(cr_type_corpss.size() - 1);
        assertThat(testCr_type_corps.getTc_libelle_fr_fr()).isEqualTo(DEFAULT_TC_LIBELLE_FR_FR);
    }

    @Test
    @Transactional
    public void checkTc_libelle_fr_frIsRequired() throws Exception {
        int databaseSizeBeforeTest = cr_type_corpsRepository.findAll().size();
        // set the field null
        cr_type_corps.setTc_libelle_fr_fr(null);

        // Create the Cr_type_corps, which fails.
        Cr_type_corpsDTO cr_type_corpsDTO = cr_type_corpsMapper.cr_type_corpsToCr_type_corpsDTO(cr_type_corps);

        restCr_type_corpsMockMvc.perform(post("/api/cr_type_corpss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_type_corpsDTO)))
                .andExpect(status().isBadRequest());

        List<Cr_type_corps> cr_type_corpss = cr_type_corpsRepository.findAll();
        assertThat(cr_type_corpss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCr_type_corpss() throws Exception {
        // Initialize the database
        cr_type_corpsRepository.saveAndFlush(cr_type_corps);

        // Get all the cr_type_corpss
        restCr_type_corpsMockMvc.perform(get("/api/cr_type_corpss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_type_corps.getId().intValue())))
                .andExpect(jsonPath("$.[*].tc_libelle_fr_fr").value(hasItem(DEFAULT_TC_LIBELLE_FR_FR.toString())));
    }

    @Test
    @Transactional
    public void getCr_type_corps() throws Exception {
        // Initialize the database
        cr_type_corpsRepository.saveAndFlush(cr_type_corps);

        // Get the cr_type_corps
        restCr_type_corpsMockMvc.perform(get("/api/cr_type_corpss/{id}", cr_type_corps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_type_corps.getId().intValue()))
            .andExpect(jsonPath("$.tc_libelle_fr_fr").value(DEFAULT_TC_LIBELLE_FR_FR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_type_corps() throws Exception {
        // Get the cr_type_corps
        restCr_type_corpsMockMvc.perform(get("/api/cr_type_corpss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_type_corps() throws Exception {
        // Initialize the database
        cr_type_corpsRepository.saveAndFlush(cr_type_corps);

		int databaseSizeBeforeUpdate = cr_type_corpsRepository.findAll().size();

        // Update the cr_type_corps
        cr_type_corps.setTc_libelle_fr_fr(UPDATED_TC_LIBELLE_FR_FR);
        Cr_type_corpsDTO cr_type_corpsDTO = cr_type_corpsMapper.cr_type_corpsToCr_type_corpsDTO(cr_type_corps);

        restCr_type_corpsMockMvc.perform(put("/api/cr_type_corpss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_type_corpsDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_type_corps in the database
        List<Cr_type_corps> cr_type_corpss = cr_type_corpsRepository.findAll();
        assertThat(cr_type_corpss).hasSize(databaseSizeBeforeUpdate);
        Cr_type_corps testCr_type_corps = cr_type_corpss.get(cr_type_corpss.size() - 1);
        assertThat(testCr_type_corps.getTc_libelle_fr_fr()).isEqualTo(UPDATED_TC_LIBELLE_FR_FR);
    }

    @Test
    @Transactional
    public void deleteCr_type_corps() throws Exception {
        // Initialize the database
        cr_type_corpsRepository.saveAndFlush(cr_type_corps);

		int databaseSizeBeforeDelete = cr_type_corpsRepository.findAll().size();

        // Get the cr_type_corps
        restCr_type_corpsMockMvc.perform(delete("/api/cr_type_corpss/{id}", cr_type_corps.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_type_corps> cr_type_corpss = cr_type_corpsRepository.findAll();
        assertThat(cr_type_corpss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
