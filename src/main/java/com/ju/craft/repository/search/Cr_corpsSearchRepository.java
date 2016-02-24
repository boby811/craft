package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_corps;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_corps entity.
 */
public interface Cr_corpsSearchRepository extends ElasticsearchRepository<Cr_corps, Long> {
}
