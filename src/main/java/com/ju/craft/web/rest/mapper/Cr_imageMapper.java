package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_imageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_image and its DTO Cr_imageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_imageMapper {

    Cr_imageDTO cr_imageToCr_imageDTO(Cr_image cr_image);

    @Mapping(target = "cr_elements", ignore = true)
    @Mapping(target = "cr_categorie_crafts", ignore = true)
    Cr_image cr_imageDTOToCr_image(Cr_imageDTO cr_imageDTO);
}
