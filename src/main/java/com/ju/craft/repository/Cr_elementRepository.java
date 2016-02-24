package com.ju.craft.repository;

import com.ju.craft.domain.Cr_element;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Cr_element entity.
 */
public interface Cr_elementRepository extends JpaRepository<Cr_element,Long> {

    @Query("select distinct cr_element from Cr_element cr_element left join fetch cr_element.cr_images")
    List<Cr_element> findAllWithEagerRelationships();

    @Query("select cr_element from Cr_element cr_element left join fetch cr_element.cr_images where cr_element.id =:id")
    Cr_element findOneWithEagerRelationships(@Param("id") Long id);

}
