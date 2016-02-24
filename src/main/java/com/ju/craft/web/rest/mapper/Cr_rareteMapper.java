package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_rareteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_rarete and its DTO Cr_rareteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_rareteMapper {

    Cr_rareteDTO cr_rareteToCr_rareteDTO(Cr_rarete cr_rarete);

    @Mapping(target = "cr_elements", ignore = true)
    Cr_rarete cr_rareteDTOToCr_rarete(Cr_rareteDTO cr_rareteDTO);
}
