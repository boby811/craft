package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_puissance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_puissance entity.
 */
public interface Cr_puissanceSearchRepository extends ElasticsearchRepository<Cr_puissance, Long> {
}
