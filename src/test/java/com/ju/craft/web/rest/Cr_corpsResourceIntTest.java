package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_corps;
import com.ju.craft.repository.Cr_corpsRepository;
import com.ju.craft.repository.search.Cr_corpsSearchRepository;
import com.ju.craft.web.rest.dto.Cr_corpsDTO;
import com.ju.craft.web.rest.mapper.Cr_corpsMapper;

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
 * Test class for the Cr_corpsResource REST controller.
 *
 * @see Cr_corpsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_corpsResourceIntTest {

    private static final String DEFAULT_CO_NOM_FR_FR = "AAAAA";
    private static final String UPDATED_CO_NOM_FR_FR = "BBBBB";

    @Inject
    private Cr_corpsRepository cr_corpsRepository;

    @Inject
    private Cr_corpsMapper cr_corpsMapper;

    @Inject
    private Cr_corpsSearchRepository cr_corpsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_corpsMockMvc;

    private Cr_corps cr_corps;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_corpsResource cr_corpsResource = new Cr_corpsResource();
        ReflectionTestUtils.setField(cr_corpsResource, "cr_corpsSearchRepository", cr_corpsSearchRepository);
        ReflectionTestUtils.setField(cr_corpsResource, "cr_corpsRepository", cr_corpsRepository);
        ReflectionTestUtils.setField(cr_corpsResource, "cr_corpsMapper", cr_corpsMapper);
        this.restCr_corpsMockMvc = MockMvcBuilders.standaloneSetup(cr_corpsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_corps = new Cr_corps();
        cr_corps.setCo_nom_fr_fr(DEFAULT_CO_NOM_FR_FR);
    }

    @Test
    @Transactional
    public void createCr_corps() throws Exception {
        int databaseSizeBeforeCreate = cr_corpsRepository.findAll().size();

        // Create the Cr_corps
        Cr_corpsDTO cr_corpsDTO = cr_corpsMapper.cr_corpsToCr_corpsDTO(cr_corps);

        restCr_corpsMockMvc.perform(post("/api/cr_corpss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_corpsDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_corps in the database
        List<Cr_corps> cr_corpss = cr_corpsRepository.findAll();
        assertThat(cr_corpss).hasSize(databaseSizeBeforeCreate + 1);
        Cr_corps testCr_corps = cr_corpss.get(cr_corpss.size() - 1);
        assertThat(testCr_corps.getCo_nom_fr_fr()).isEqualTo(DEFAULT_CO_NOM_FR_FR);
    }

    @Test
    @Transactional
    public void getAllCr_corpss() throws Exception {
        // Initialize the database
        cr_corpsRepository.saveAndFlush(cr_corps);

        // Get all the cr_corpss
        restCr_corpsMockMvc.perform(get("/api/cr_corpss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_corps.getId().intValue())))
                .andExpect(jsonPath("$.[*].co_nom_fr_fr").value(hasItem(DEFAULT_CO_NOM_FR_FR.toString())));
    }

    @Test
    @Transactional
    public void getCr_corps() throws Exception {
        // Initialize the database
        cr_corpsRepository.saveAndFlush(cr_corps);

        // Get the cr_corps
        restCr_corpsMockMvc.perform(get("/api/cr_corpss/{id}", cr_corps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_corps.getId().intValue()))
            .andExpect(jsonPath("$.co_nom_fr_fr").value(DEFAULT_CO_NOM_FR_FR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_corps() throws Exception {
        // Get the cr_corps
        restCr_corpsMockMvc.perform(get("/api/cr_corpss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_corps() throws Exception {
        // Initialize the database
        cr_corpsRepository.saveAndFlush(cr_corps);

		int databaseSizeBeforeUpdate = cr_corpsRepository.findAll().size();

        // Update the cr_corps
        cr_corps.setCo_nom_fr_fr(UPDATED_CO_NOM_FR_FR);
        Cr_corpsDTO cr_corpsDTO = cr_corpsMapper.cr_corpsToCr_corpsDTO(cr_corps);

        restCr_corpsMockMvc.perform(put("/api/cr_corpss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_corpsDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_corps in the database
        List<Cr_corps> cr_corpss = cr_corpsRepository.findAll();
        assertThat(cr_corpss).hasSize(databaseSizeBeforeUpdate);
        Cr_corps testCr_corps = cr_corpss.get(cr_corpss.size() - 1);
        assertThat(testCr_corps.getCo_nom_fr_fr()).isEqualTo(UPDATED_CO_NOM_FR_FR);
    }

    @Test
    @Transactional
    public void deleteCr_corps() throws Exception {
        // Initialize the database
        cr_corpsRepository.saveAndFlush(cr_corps);

		int databaseSizeBeforeDelete = cr_corpsRepository.findAll().size();

        // Get the cr_corps
        restCr_corpsMockMvc.perform(delete("/api/cr_corpss/{id}", cr_corps.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_corps> cr_corpss = cr_corpsRepository.findAll();
        assertThat(cr_corpss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
