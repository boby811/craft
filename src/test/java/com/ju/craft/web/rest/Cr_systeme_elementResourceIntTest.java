package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_systeme_element;
import com.ju.craft.repository.Cr_systeme_elementRepository;
import com.ju.craft.repository.search.Cr_systeme_elementSearchRepository;
import com.ju.craft.web.rest.dto.Cr_systeme_elementDTO;
import com.ju.craft.web.rest.mapper.Cr_systeme_elementMapper;

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
 * Test class for the Cr_systeme_elementResource REST controller.
 *
 * @see Cr_systeme_elementResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_systeme_elementResourceIntTest {

    private static final String DEFAULT_SE_QUANTITE = "AAAAA";
    private static final String UPDATED_SE_QUANTITE = "BBBBB";

    @Inject
    private Cr_systeme_elementRepository cr_systeme_elementRepository;

    @Inject
    private Cr_systeme_elementMapper cr_systeme_elementMapper;

    @Inject
    private Cr_systeme_elementSearchRepository cr_systeme_elementSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_systeme_elementMockMvc;

    private Cr_systeme_element cr_systeme_element;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_systeme_elementResource cr_systeme_elementResource = new Cr_systeme_elementResource();
        ReflectionTestUtils.setField(cr_systeme_elementResource, "cr_systeme_elementSearchRepository", cr_systeme_elementSearchRepository);
        ReflectionTestUtils.setField(cr_systeme_elementResource, "cr_systeme_elementRepository", cr_systeme_elementRepository);
        ReflectionTestUtils.setField(cr_systeme_elementResource, "cr_systeme_elementMapper", cr_systeme_elementMapper);
        this.restCr_systeme_elementMockMvc = MockMvcBuilders.standaloneSetup(cr_systeme_elementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_systeme_element = new Cr_systeme_element();
        cr_systeme_element.setSe_quantite(DEFAULT_SE_QUANTITE);
    }

    @Test
    @Transactional
    public void createCr_systeme_element() throws Exception {
        int databaseSizeBeforeCreate = cr_systeme_elementRepository.findAll().size();

        // Create the Cr_systeme_element
        Cr_systeme_elementDTO cr_systeme_elementDTO = cr_systeme_elementMapper.cr_systeme_elementToCr_systeme_elementDTO(cr_systeme_element);

        restCr_systeme_elementMockMvc.perform(post("/api/cr_systeme_elements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_systeme_elementDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_systeme_element in the database
        List<Cr_systeme_element> cr_systeme_elements = cr_systeme_elementRepository.findAll();
        assertThat(cr_systeme_elements).hasSize(databaseSizeBeforeCreate + 1);
        Cr_systeme_element testCr_systeme_element = cr_systeme_elements.get(cr_systeme_elements.size() - 1);
        assertThat(testCr_systeme_element.getSe_quantite()).isEqualTo(DEFAULT_SE_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllCr_systeme_elements() throws Exception {
        // Initialize the database
        cr_systeme_elementRepository.saveAndFlush(cr_systeme_element);

        // Get all the cr_systeme_elements
        restCr_systeme_elementMockMvc.perform(get("/api/cr_systeme_elements?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_systeme_element.getId().intValue())))
                .andExpect(jsonPath("$.[*].se_quantite").value(hasItem(DEFAULT_SE_QUANTITE.toString())));
    }

    @Test
    @Transactional
    public void getCr_systeme_element() throws Exception {
        // Initialize the database
        cr_systeme_elementRepository.saveAndFlush(cr_systeme_element);

        // Get the cr_systeme_element
        restCr_systeme_elementMockMvc.perform(get("/api/cr_systeme_elements/{id}", cr_systeme_element.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_systeme_element.getId().intValue()))
            .andExpect(jsonPath("$.se_quantite").value(DEFAULT_SE_QUANTITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_systeme_element() throws Exception {
        // Get the cr_systeme_element
        restCr_systeme_elementMockMvc.perform(get("/api/cr_systeme_elements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_systeme_element() throws Exception {
        // Initialize the database
        cr_systeme_elementRepository.saveAndFlush(cr_systeme_element);

		int databaseSizeBeforeUpdate = cr_systeme_elementRepository.findAll().size();

        // Update the cr_systeme_element
        cr_systeme_element.setSe_quantite(UPDATED_SE_QUANTITE);
        Cr_systeme_elementDTO cr_systeme_elementDTO = cr_systeme_elementMapper.cr_systeme_elementToCr_systeme_elementDTO(cr_systeme_element);

        restCr_systeme_elementMockMvc.perform(put("/api/cr_systeme_elements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_systeme_elementDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_systeme_element in the database
        List<Cr_systeme_element> cr_systeme_elements = cr_systeme_elementRepository.findAll();
        assertThat(cr_systeme_elements).hasSize(databaseSizeBeforeUpdate);
        Cr_systeme_element testCr_systeme_element = cr_systeme_elements.get(cr_systeme_elements.size() - 1);
        assertThat(testCr_systeme_element.getSe_quantite()).isEqualTo(UPDATED_SE_QUANTITE);
    }

    @Test
    @Transactional
    public void deleteCr_systeme_element() throws Exception {
        // Initialize the database
        cr_systeme_elementRepository.saveAndFlush(cr_systeme_element);

		int databaseSizeBeforeDelete = cr_systeme_elementRepository.findAll().size();

        // Get the cr_systeme_element
        restCr_systeme_elementMockMvc.perform(delete("/api/cr_systeme_elements/{id}", cr_systeme_element.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_systeme_element> cr_systeme_elements = cr_systeme_elementRepository.findAll();
        assertThat(cr_systeme_elements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
