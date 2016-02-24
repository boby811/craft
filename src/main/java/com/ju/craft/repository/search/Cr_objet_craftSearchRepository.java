package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_objet_craft;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_objet_craft entity.
 */
public interface Cr_objet_craftSearchRepository extends ElasticsearchRepository<Cr_objet_craft, Long> {
}
