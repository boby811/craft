package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_element;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_element entity.
 */
public interface Cr_elementSearchRepository extends ElasticsearchRepository<Cr_element, Long> {
}
