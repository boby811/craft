package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_systeme;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_systeme entity.
 */
public interface Cr_systemeSearchRepository extends ElasticsearchRepository<Cr_systeme, Long> {
}
