package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_rarete;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_rarete entity.
 */
public interface Cr_rareteSearchRepository extends ElasticsearchRepository<Cr_rarete, Long> {
}
