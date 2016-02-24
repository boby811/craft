package com.ju.craft.service;

import java.util.Collection;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import com.ju.craft.domain.*;
import com.ju.craft.repository.*;
import com.ju.craft.repository.search.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class ElasticsearchIndexService {
	
	public final int nombreParDecoupe = 200000;
    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    @Inject
    private Cr_categorie_craftRepository cr_categorie_craftRepository;

    @Inject
    private Cr_categorie_craftSearchRepository cr_categorie_craftSearchRepository;

    @Inject
    private Cr_corpsRepository cr_corpsRepository;

    @Inject
    private Cr_corpsSearchRepository cr_corpsSearchRepository;

    @Inject
    private Cr_corps_elementRepository cr_corps_elementRepository;

    @Inject
    private Cr_corps_elementSearchRepository cr_corps_elementSearchRepository;

    @Inject
    private Cr_elementRepository cr_elementRepository;

    @Inject
    private Cr_elementSearchRepository cr_elementSearchRepository;

    @Inject
    private Cr_imageRepository cr_imageRepository;

    @Inject
    private Cr_imageSearchRepository cr_imageSearchRepository;

    @Inject
    private Cr_objet_craftRepository cr_objet_craftRepository;

    @Inject
    private Cr_objet_craftSearchRepository cr_objet_craftSearchRepository;

    @Inject
    private Cr_puissanceRepository cr_puissanceRepository;

    @Inject
    private Cr_puissanceSearchRepository cr_puissanceSearchRepository;

    @Inject
    private Cr_rareteRepository cr_rareteRepository;

    @Inject
    private Cr_rareteSearchRepository cr_rareteSearchRepository;

    @Inject
    private Cr_systemeRepository cr_systemeRepository;

    @Inject
    private Cr_systemeSearchRepository cr_systemeSearchRepository;

    @Inject
    private Cr_systeme_elementRepository cr_systeme_elementRepository;

    @Inject
    private Cr_systeme_elementSearchRepository cr_systeme_elementSearchRepository;

    @Inject
    private Cr_type_corpsRepository cr_type_corpsRepository;

    @Inject
    private Cr_type_corpsSearchRepository cr_type_corpsSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserSearchRepository userSearchRepository;

    @Inject
    private ElasticsearchTemplate elasticsearchTemplate;

    @Async
    @Timed
    public void reindexAll() {
        elasticsearchTemplate.deleteIndex(Cr_categorie_craft.class);
        if (cr_categorie_craftRepository.count() > 0) {
            cr_categorie_craftSearchRepository.save(cr_categorie_craftRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_categorie_crafts");

        elasticsearchTemplate.deleteIndex(Cr_corps.class);
        if (cr_corpsRepository.count() > 0) {
            cr_corpsSearchRepository.save(cr_corpsRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_corpss");

        elasticsearchTemplate.deleteIndex(Cr_corps_element.class);
        if (cr_corps_elementRepository.count() > 0) {
            cr_corps_elementSearchRepository.save(cr_corps_elementRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_corps_elements");

        elasticsearchTemplate.deleteIndex(Cr_element.class);
        if (cr_elementRepository.count() > 0) {
            cr_elementSearchRepository.save(cr_elementRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_elements");

        elasticsearchTemplate.deleteIndex(Cr_image.class);
        if (cr_imageRepository.count() > 0) {
            cr_imageSearchRepository.save(cr_imageRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_images");

        elasticsearchTemplate.deleteIndex(Cr_objet_craft.class);
        if (cr_objet_craftRepository.count() > 0) {
            cr_objet_craftSearchRepository.save(cr_objet_craftRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_objet_crafts");

        elasticsearchTemplate.deleteIndex(Cr_puissance.class);
        if (cr_puissanceRepository.count() > 0) {
            cr_puissanceSearchRepository.save(cr_puissanceRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_puissances");

        elasticsearchTemplate.deleteIndex(Cr_rarete.class);
        if (cr_rareteRepository.count() > 0) {
            cr_rareteSearchRepository.save(cr_rareteRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_raretes");

        elasticsearchTemplate.deleteIndex(Cr_systeme.class);
        if (cr_systemeRepository.count() > 0) {
        	List<Cr_systeme> listSy = cr_systemeRepository.findAll();
        	log.info("Elasticsearch: nb Systeme : " + listSy.size());
        	log.info("Elasticsearch: Nombre de fois : "+ nombreParDecoupe + " : " + listSy.size()/nombreParDecoupe);
        	int nbIteration = listSy.size()/nombreParDecoupe;
        	int firstIndex = 0;
        	int tempIndex = nombreParDecoupe -  1;
        	int lastIndex = listSy.size()-1;
        	log.info("Début elasticSearch save systeme par " +nombreParDecoupe);
        	for (int i = 0; i < nbIteration; i++) {
        		log.info("------" + i + "/" + (nbIteration - 1)  + "save index de " + firstIndex + " a " + tempIndex);
        		cr_systemeSearchRepository.save(listSy.subList(firstIndex, tempIndex));
        		log.info("------" + i + "/" + (nbIteration - 1)  + "save index de " + firstIndex + " a " + tempIndex + " -- OK --");
        		firstIndex = tempIndex;
        		if (i<nbIteration-1){
        			tempIndex = tempIndex + nombreParDecoupe;
        		}
			}
        	if(tempIndex<lastIndex){
        		log.info("------save index restant de " + tempIndex + " a " + lastIndex);
        		cr_systemeSearchRepository.save(listSy.subList(tempIndex, lastIndex));
        		log.info("------save index restant de " + tempIndex + " a " + lastIndex + " -- OK --");
        	}
        }
        log.info("Elasticsearch: Indexed all cr_systemes");

        elasticsearchTemplate.deleteIndex(Cr_systeme_element.class);
        if (cr_systeme_elementRepository.count() > 0) {
            cr_systeme_elementSearchRepository.save(cr_systeme_elementRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_systeme_elements");

        elasticsearchTemplate.deleteIndex(Cr_type_corps.class);
        if (cr_type_corpsRepository.count() > 0) {
            cr_type_corpsSearchRepository.save(cr_type_corpsRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all cr_type_corpss");

        elasticsearchTemplate.deleteIndex(User.class);
        if (userRepository.count() > 0) {
            userSearchRepository.save(userRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all users");

        log.info("Elasticsearch: Successfully performed reindexing");
    }
}
