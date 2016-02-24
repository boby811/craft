package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_systemeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_systeme and its DTO Cr_systemeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_systemeMapper {

    Cr_systemeDTO cr_systemeToCr_systemeDTO(Cr_systeme cr_systeme);

    @Mapping(target = "cr_systeme_elements", ignore = true)
    @Mapping(target = "cr_corpss", ignore = true)
    Cr_systeme cr_systemeDTOToCr_systeme(Cr_systemeDTO cr_systemeDTO);
}
