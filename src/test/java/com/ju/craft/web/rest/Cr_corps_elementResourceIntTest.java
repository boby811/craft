package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_corps_element;
import com.ju.craft.repository.Cr_corps_elementRepository;
import com.ju.craft.repository.search.Cr_corps_elementSearchRepository;
import com.ju.craft.web.rest.dto.Cr_corps_elementDTO;
import com.ju.craft.web.rest.mapper.Cr_corps_elementMapper;

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
 * Test class for the Cr_corps_elementResource REST controller.
 *
 * @see Cr_corps_elementResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_corps_elementResourceIntTest {

    private static final String DEFAULT_CE_QUANTITE = "AAAAA";
    private static final String UPDATED_CE_QUANTITE = "BBBBB";

    @Inject
    private Cr_corps_elementRepository cr_corps_elementRepository;

    @Inject
    private Cr_corps_elementMapper cr_corps_elementMapper;

    @Inject
    private Cr_corps_elementSearchRepository cr_corps_elementSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_corps_elementMockMvc;

    private Cr_corps_element cr_corps_element;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_corps_elementResource cr_corps_elementResource = new Cr_corps_elementResource();
        ReflectionTestUtils.setField(cr_corps_elementResource, "cr_corps_elementSearchRepository", cr_corps_elementSearchRepository);
        ReflectionTestUtils.setField(cr_corps_elementResource, "cr_corps_elementRepository", cr_corps_elementRepository);
        ReflectionTestUtils.setField(cr_corps_elementResource, "cr_corps_elementMapper", cr_corps_elementMapper);
        this.restCr_corps_elementMockMvc = MockMvcBuilders.standaloneSetup(cr_corps_elementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_corps_element = new Cr_corps_element();
        cr_corps_element.setCe_quantite(DEFAULT_CE_QUANTITE);
    }

    @Test
    @Transactional
    public void createCr_corps_element() throws Exception {
        int databaseSizeBeforeCreate = cr_corps_elementRepository.findAll().size();

        // Create the Cr_corps_element
        Cr_corps_elementDTO cr_corps_elementDTO = cr_corps_elementMapper.cr_corps_elementToCr_corps_elementDTO(cr_corps_element);

        restCr_corps_elementMockMvc.perform(post("/api/cr_corps_elements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_corps_elementDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_corps_element in the database
        List<Cr_corps_element> cr_corps_elements = cr_corps_elementRepository.findAll();
        assertThat(cr_corps_elements).hasSize(databaseSizeBeforeCreate + 1);
        Cr_corps_element testCr_corps_element = cr_corps_elements.get(cr_corps_elements.size() - 1);
        assertThat(testCr_corps_element.getCe_quantite()).isEqualTo(DEFAULT_CE_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllCr_corps_elements() throws Exception {
        // Initialize the database
        cr_corps_elementRepository.saveAndFlush(cr_corps_element);

        // Get all the cr_corps_elements
        restCr_corps_elementMockMvc.perform(get("/api/cr_corps_elements?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_corps_element.getId().intValue())))
                .andExpect(jsonPath("$.[*].ce_quantite").value(hasItem(DEFAULT_CE_QUANTITE.toString())));
    }

    @Test
    @Transactional
    public void getCr_corps_element() throws Exception {
        // Initialize the database
        cr_corps_elementRepository.saveAndFlush(cr_corps_element);

        // Get the cr_corps_element
        restCr_corps_elementMockMvc.perform(get("/api/cr_corps_elements/{id}", cr_corps_element.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_corps_element.getId().intValue()))
            .andExpect(jsonPath("$.ce_quantite").value(DEFAULT_CE_QUANTITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_corps_element() throws Exception {
        // Get the cr_corps_element
        restCr_corps_elementMockMvc.perform(get("/api/cr_corps_elements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_corps_element() throws Exception {
        // Initialize the database
        cr_corps_elementRepository.saveAndFlush(cr_corps_element);

		int databaseSizeBeforeUpdate = cr_corps_elementRepository.findAll().size();

        // Update the cr_corps_element
        cr_corps_element.setCe_quantite(UPDATED_CE_QUANTITE);
        Cr_corps_elementDTO cr_corps_elementDTO = cr_corps_elementMapper.cr_corps_elementToCr_corps_elementDTO(cr_corps_element);

        restCr_corps_elementMockMvc.perform(put("/api/cr_corps_elements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_corps_elementDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_corps_element in the database
        List<Cr_corps_element> cr_corps_elements = cr_corps_elementRepository.findAll();
        assertThat(cr_corps_elements).hasSize(databaseSizeBeforeUpdate);
        Cr_corps_element testCr_corps_element = cr_corps_elements.get(cr_corps_elements.size() - 1);
        assertThat(testCr_corps_element.getCe_quantite()).isEqualTo(UPDATED_CE_QUANTITE);
    }

    @Test
    @Transactional
    public void deleteCr_corps_element() throws Exception {
        // Initialize the database
        cr_corps_elementRepository.saveAndFlush(cr_corps_element);

		int databaseSizeBeforeDelete = cr_corps_elementRepository.findAll().size();

        // Get the cr_corps_element
        restCr_corps_elementMockMvc.perform(delete("/api/cr_corps_elements/{id}", cr_corps_element.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_corps_element> cr_corps_elements = cr_corps_elementRepository.findAll();
        assertThat(cr_corps_elements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
