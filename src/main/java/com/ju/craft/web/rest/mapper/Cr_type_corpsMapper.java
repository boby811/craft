package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_type_corpsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_type_corps and its DTO Cr_type_corpsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_type_corpsMapper {

    Cr_type_corpsDTO cr_type_corpsToCr_type_corpsDTO(Cr_type_corps cr_type_corps);

    @Mapping(target = "cr_corpss", ignore = true)
    Cr_type_corps cr_type_corpsDTOToCr_type_corps(Cr_type_corpsDTO cr_type_corpsDTO);
}
