package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_categorie_craft;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_categorie_craft entity.
 */
public interface Cr_categorie_craftSearchRepository extends ElasticsearchRepository<Cr_categorie_craft, Long> {
}
