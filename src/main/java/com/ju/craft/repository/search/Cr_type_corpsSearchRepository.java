package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_type_corps;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_type_corps entity.
 */
public interface Cr_type_corpsSearchRepository extends ElasticsearchRepository<Cr_type_corps, Long> {
}
