package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_element;
import com.ju.craft.repository.Cr_elementRepository;
import com.ju.craft.repository.search.Cr_elementSearchRepository;
import com.ju.craft.web.rest.dto.Cr_elementDTO;
import com.ju.craft.web.rest.mapper.Cr_elementMapper;

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
 * Test class for the Cr_elementResource REST controller.
 *
 * @see Cr_elementResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_elementResourceIntTest {

    private static final String DEFAULT_EL_NOM_COURT_FR_FR = "AAAAA";
    private static final String UPDATED_EL_NOM_COURT_FR_FR = "BBBBB";
    private static final String DEFAULT_EL_NOM_LONG_FR_FR = "AAAAA";
    private static final String UPDATED_EL_NOM_LONG_FR_FR = "BBBBB";
    private static final String DEFAULT_EL_DESCRIPTION_FR_FR = "AAAAA";
    private static final String UPDATED_EL_DESCRIPTION_FR_FR = "BBBBB";

    private static final Integer DEFAULT_EL_NUM_ATOMIQUE = 1;
    private static final Integer UPDATED_EL_NUM_ATOMIQUE = 2;

    private static final Integer DEFAULT_EL_POINT_FUSION = 1;
    private static final Integer UPDATED_EL_POINT_FUSION = 2;

    @Inject
    private Cr_elementRepository cr_elementRepository;

    @Inject
    private Cr_elementMapper cr_elementMapper;

    @Inject
    private Cr_elementSearchRepository cr_elementSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_elementMockMvc;

    private Cr_element cr_element;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_elementResource cr_elementResource = new Cr_elementResource();
        ReflectionTestUtils.setField(cr_elementResource, "cr_elementSearchRepository", cr_elementSearchRepository);
        ReflectionTestUtils.setField(cr_elementResource, "cr_elementRepository", cr_elementRepository);
        ReflectionTestUtils.setField(cr_elementResource, "cr_elementMapper", cr_elementMapper);
        this.restCr_elementMockMvc = MockMvcBuilders.standaloneSetup(cr_elementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_element = new Cr_element();
        cr_element.setEl_nom_court_fr_fr(DEFAULT_EL_NOM_COURT_FR_FR);
        cr_element.setEl_nom_long_fr_fr(DEFAULT_EL_NOM_LONG_FR_FR);
        cr_element.setEl_description_fr_fr(DEFAULT_EL_DESCRIPTION_FR_FR);
        cr_element.setEl_num_atomique(DEFAULT_EL_NUM_ATOMIQUE);
        cr_element.setEl_point_fusion(DEFAULT_EL_POINT_FUSION);
    }

    @Test
    @Transactional
    public void createCr_element() throws Exception {
        int databaseSizeBeforeCreate = cr_elementRepository.findAll().size();

        // Create the Cr_element
        Cr_elementDTO cr_elementDTO = cr_elementMapper.cr_elementToCr_elementDTO(cr_element);

        restCr_elementMockMvc.perform(post("/api/cr_elements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_elementDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_element in the database
        List<Cr_element> cr_elements = cr_elementRepository.findAll();
        assertThat(cr_elements).hasSize(databaseSizeBeforeCreate + 1);
        Cr_element testCr_element = cr_elements.get(cr_elements.size() - 1);
        assertThat(testCr_element.getEl_nom_court_fr_fr()).isEqualTo(DEFAULT_EL_NOM_COURT_FR_FR);
        assertThat(testCr_element.getEl_nom_long_fr_fr()).isEqualTo(DEFAULT_EL_NOM_LONG_FR_FR);
        assertThat(testCr_element.getEl_description_fr_fr()).isEqualTo(DEFAULT_EL_DESCRIPTION_FR_FR);
        assertThat(testCr_element.getEl_num_atomique()).isEqualTo(DEFAULT_EL_NUM_ATOMIQUE);
        assertThat(testCr_element.getEl_point_fusion()).isEqualTo(DEFAULT_EL_POINT_FUSION);
    }

    @Test
    @Transactional
    public void checkEl_nom_court_fr_frIsRequired() throws Exception {
        int databaseSizeBeforeTest = cr_elementRepository.findAll().size();
        // set the field null
        cr_element.setEl_nom_court_fr_fr(null);

        // Create the Cr_element, which fails.
        Cr_elementDTO cr_elementDTO = cr_elementMapper.cr_elementToCr_elementDTO(cr_element);

        restCr_elementMockMvc.perform(post("/api/cr_elements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_elementDTO)))
                .andExpect(status().isBadRequest());

        List<Cr_element> cr_elements = cr_elementRepository.findAll();
        assertThat(cr_elements).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEl_nom_long_fr_frIsRequired() throws Exception {
        int databaseSizeBeforeTest = cr_elementRepository.findAll().size();
        // set the field null
        cr_element.setEl_nom_long_fr_fr(null);

        // Create the Cr_element, which fails.
        Cr_elementDTO cr_elementDTO = cr_elementMapper.cr_elementToCr_elementDTO(cr_element);

        restCr_elementMockMvc.perform(post("/api/cr_elements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_elementDTO)))
                .andExpect(status().isBadRequest());

        List<Cr_element> cr_elements = cr_elementRepository.findAll();
        assertThat(cr_elements).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCr_elements() throws Exception {
        // Initialize the database
        cr_elementRepository.saveAndFlush(cr_element);

        // Get all the cr_elements
        restCr_elementMockMvc.perform(get("/api/cr_elements?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_element.getId().intValue())))
                .andExpect(jsonPath("$.[*].el_nom_court_fr_fr").value(hasItem(DEFAULT_EL_NOM_COURT_FR_FR.toString())))
                .andExpect(jsonPath("$.[*].el_nom_long_fr_fr").value(hasItem(DEFAULT_EL_NOM_LONG_FR_FR.toString())))
                .andExpect(jsonPath("$.[*].el_description_fr_fr").value(hasItem(DEFAULT_EL_DESCRIPTION_FR_FR.toString())))
                .andExpect(jsonPath("$.[*].el_num_atomique").value(hasItem(DEFAULT_EL_NUM_ATOMIQUE)))
                .andExpect(jsonPath("$.[*].el_point_fusion").value(hasItem(DEFAULT_EL_POINT_FUSION)));
    }

    @Test
    @Transactional
    public void getCr_element() throws Exception {
        // Initialize the database
        cr_elementRepository.saveAndFlush(cr_element);

        // Get the cr_element
        restCr_elementMockMvc.perform(get("/api/cr_elements/{id}", cr_element.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_element.getId().intValue()))
            .andExpect(jsonPath("$.el_nom_court_fr_fr").value(DEFAULT_EL_NOM_COURT_FR_FR.toString()))
            .andExpect(jsonPath("$.el_nom_long_fr_fr").value(DEFAULT_EL_NOM_LONG_FR_FR.toString()))
            .andExpect(jsonPath("$.el_description_fr_fr").value(DEFAULT_EL_DESCRIPTION_FR_FR.toString()))
            .andExpect(jsonPath("$.el_num_atomique").value(DEFAULT_EL_NUM_ATOMIQUE))
            .andExpect(jsonPath("$.el_point_fusion").value(DEFAULT_EL_POINT_FUSION));
    }

    @Test
    @Transactional
    public void getNonExistingCr_element() throws Exception {
        // Get the cr_element
        restCr_elementMockMvc.perform(get("/api/cr_elements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_element() throws Exception {
        // Initialize the database
        cr_elementRepository.saveAndFlush(cr_element);

		int databaseSizeBeforeUpdate = cr_elementRepository.findAll().size();

        // Update the cr_element
        cr_element.setEl_nom_court_fr_fr(UPDATED_EL_NOM_COURT_FR_FR);
        cr_element.setEl_nom_long_fr_fr(UPDATED_EL_NOM_LONG_FR_FR);
        cr_element.setEl_description_fr_fr(UPDATED_EL_DESCRIPTION_FR_FR);
        cr_element.setEl_num_atomique(UPDATED_EL_NUM_ATOMIQUE);
        cr_element.setEl_point_fusion(UPDATED_EL_POINT_FUSION);
        Cr_elementDTO cr_elementDTO = cr_elementMapper.cr_elementToCr_elementDTO(cr_element);

        restCr_elementMockMvc.perform(put("/api/cr_elements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_elementDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_element in the database
        List<Cr_element> cr_elements = cr_elementRepository.findAll();
        assertThat(cr_elements).hasSize(databaseSizeBeforeUpdate);
        Cr_element testCr_element = cr_elements.get(cr_elements.size() - 1);
        assertThat(testCr_element.getEl_nom_court_fr_fr()).isEqualTo(UPDATED_EL_NOM_COURT_FR_FR);
        assertThat(testCr_element.getEl_nom_long_fr_fr()).isEqualTo(UPDATED_EL_NOM_LONG_FR_FR);
        assertThat(testCr_element.getEl_description_fr_fr()).isEqualTo(UPDATED_EL_DESCRIPTION_FR_FR);
        assertThat(testCr_element.getEl_num_atomique()).isEqualTo(UPDATED_EL_NUM_ATOMIQUE);
        assertThat(testCr_element.getEl_point_fusion()).isEqualTo(UPDATED_EL_POINT_FUSION);
    }

    @Test
    @Transactional
    public void deleteCr_element() throws Exception {
        // Initialize the database
        cr_elementRepository.saveAndFlush(cr_element);

		int databaseSizeBeforeDelete = cr_elementRepository.findAll().size();

        // Get the cr_element
        restCr_elementMockMvc.perform(delete("/api/cr_elements/{id}", cr_element.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_element> cr_elements = cr_elementRepository.findAll();
        assertThat(cr_elements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
