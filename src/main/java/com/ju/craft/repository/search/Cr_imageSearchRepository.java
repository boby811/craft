package com.ju.craft.repository.search;

import com.ju.craft.domain.Cr_image;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cr_image entity.
 */
public interface Cr_imageSearchRepository extends ElasticsearchRepository<Cr_image, Long> {
}
