package com.ju.craft.web.rest;

import com.ju.craft.Application;
import com.ju.craft.domain.Cr_image;
import com.ju.craft.repository.Cr_imageRepository;
import com.ju.craft.repository.search.Cr_imageSearchRepository;
import com.ju.craft.web.rest.dto.Cr_imageDTO;
import com.ju.craft.web.rest.mapper.Cr_imageMapper;

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
 * Test class for the Cr_imageResource REST controller.
 *
 * @see Cr_imageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Cr_imageResourceIntTest {

    private static final String DEFAULT_IM_URL = "AAAAA";
    private static final String UPDATED_IM_URL = "BBBBB";

    @Inject
    private Cr_imageRepository cr_imageRepository;

    @Inject
    private Cr_imageMapper cr_imageMapper;

    @Inject
    private Cr_imageSearchRepository cr_imageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCr_imageMockMvc;

    private Cr_image cr_image;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cr_imageResource cr_imageResource = new Cr_imageResource();
        ReflectionTestUtils.setField(cr_imageResource, "cr_imageSearchRepository", cr_imageSearchRepository);
        ReflectionTestUtils.setField(cr_imageResource, "cr_imageRepository", cr_imageRepository);
        ReflectionTestUtils.setField(cr_imageResource, "cr_imageMapper", cr_imageMapper);
        this.restCr_imageMockMvc = MockMvcBuilders.standaloneSetup(cr_imageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cr_image = new Cr_image();
        cr_image.setIm_url(DEFAULT_IM_URL);
    }

    @Test
    @Transactional
    public void createCr_image() throws Exception {
        int databaseSizeBeforeCreate = cr_imageRepository.findAll().size();

        // Create the Cr_image
        Cr_imageDTO cr_imageDTO = cr_imageMapper.cr_imageToCr_imageDTO(cr_image);

        restCr_imageMockMvc.perform(post("/api/cr_images")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_imageDTO)))
                .andExpect(status().isCreated());

        // Validate the Cr_image in the database
        List<Cr_image> cr_images = cr_imageRepository.findAll();
        assertThat(cr_images).hasSize(databaseSizeBeforeCreate + 1);
        Cr_image testCr_image = cr_images.get(cr_images.size() - 1);
        assertThat(testCr_image.getIm_url()).isEqualTo(DEFAULT_IM_URL);
    }

    @Test
    @Transactional
    public void getAllCr_images() throws Exception {
        // Initialize the database
        cr_imageRepository.saveAndFlush(cr_image);

        // Get all the cr_images
        restCr_imageMockMvc.perform(get("/api/cr_images?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cr_image.getId().intValue())))
                .andExpect(jsonPath("$.[*].im_url").value(hasItem(DEFAULT_IM_URL.toString())));
    }

    @Test
    @Transactional
    public void getCr_image() throws Exception {
        // Initialize the database
        cr_imageRepository.saveAndFlush(cr_image);

        // Get the cr_image
        restCr_imageMockMvc.perform(get("/api/cr_images/{id}", cr_image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cr_image.getId().intValue()))
            .andExpect(jsonPath("$.im_url").value(DEFAULT_IM_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCr_image() throws Exception {
        // Get the cr_image
        restCr_imageMockMvc.perform(get("/api/cr_images/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCr_image() throws Exception {
        // Initialize the database
        cr_imageRepository.saveAndFlush(cr_image);

		int databaseSizeBeforeUpdate = cr_imageRepository.findAll().size();

        // Update the cr_image
        cr_image.setIm_url(UPDATED_IM_URL);
        Cr_imageDTO cr_imageDTO = cr_imageMapper.cr_imageToCr_imageDTO(cr_image);

        restCr_imageMockMvc.perform(put("/api/cr_images")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cr_imageDTO)))
                .andExpect(status().isOk());

        // Validate the Cr_image in the database
        List<Cr_image> cr_images = cr_imageRepository.findAll();
        assertThat(cr_images).hasSize(databaseSizeBeforeUpdate);
        Cr_image testCr_image = cr_images.get(cr_images.size() - 1);
        assertThat(testCr_image.getIm_url()).isEqualTo(UPDATED_IM_URL);
    }

    @Test
    @Transactional
    public void deleteCr_image() throws Exception {
        // Initialize the database
        cr_imageRepository.saveAndFlush(cr_image);

		int databaseSizeBeforeDelete = cr_imageRepository.findAll().size();

        // Get the cr_image
        restCr_imageMockMvc.perform(delete("/api/cr_images/{id}", cr_image.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cr_image> cr_images = cr_imageRepository.findAll();
        assertThat(cr_images).hasSize(databaseSizeBeforeDelete - 1);
    }
}
