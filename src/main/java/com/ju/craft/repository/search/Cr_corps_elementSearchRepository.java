package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_corps_element;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_corps_element entity.
 */
public interface Cr_corps_elementSearchRepository extends ElasticsearchRepository<Cr_corps_element, Long> {
}
