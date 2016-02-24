package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_puissanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_puissance and its DTO Cr_puissanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_puissanceMapper {

    Cr_puissanceDTO cr_puissanceToCr_puissanceDTO(Cr_puissance cr_puissance);

    @Mapping(target = "cr_objet_crafts", ignore = true)
    Cr_puissance cr_puissanceDTOToCr_puissance(Cr_puissanceDTO cr_puissanceDTO);
}
