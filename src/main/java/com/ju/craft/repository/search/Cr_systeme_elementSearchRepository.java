package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_systeme_element;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_systeme_element entity.
 */
public interface Cr_systeme_elementSearchRepository extends ElasticsearchRepository<Cr_systeme_element, Long> {
}
