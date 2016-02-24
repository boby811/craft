package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_categorie_craftDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_categorie_craft and its DTO Cr_categorie_craftDTO.
 */
@Mapper(componentModel = "spring", uses = {Cr_imageMapper.class, })
public interface Cr_categorie_craftMapper {

    Cr_categorie_craftDTO cr_categorie_craftToCr_categorie_craftDTO(Cr_categorie_craft cr_categorie_craft);

    @Mapping(target = "cr_objet_crafts", ignore = true)
    Cr_categorie_craft cr_categorie_craftDTOToCr_categorie_craft(Cr_categorie_craftDTO cr_categorie_craftDTO);

    default Cr_image cr_imageFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_image cr_image = new Cr_image();
        cr_image.setId(id);
        return cr_image;
    }
}
