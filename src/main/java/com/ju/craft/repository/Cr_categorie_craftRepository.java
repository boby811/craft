package com.ju.craft.repository;

import com.ju.craft.domain.Cr_categorie_craft;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Cr_categorie_craft entity.
 */
public interface Cr_categorie_craftRepository extends JpaRepository<Cr_categorie_craft,Long> {

    @Query("select distinct cr_categorie_craft from Cr_categorie_craft cr_categorie_craft left join fetch cr_categorie_craft.cr_images")
    List<Cr_categorie_craft> findAllWithEagerRelationships();

    @Query("select cr_categorie_craft from Cr_categorie_craft cr_categorie_craft left join fetch cr_categorie_craft.cr_images where cr_categorie_craft.id =:id")
    Cr_categorie_craft findOneWithEagerRelationships(@Param("id") Long id);

}
