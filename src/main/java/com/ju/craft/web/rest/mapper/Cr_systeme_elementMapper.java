package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_systeme_elementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_systeme_element and its DTO Cr_systeme_elementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_systeme_elementMapper {

    @Mapping(source = "cr_element.id", target = "cr_elementId")
    @Mapping(source = "cr_element.el_nom_long_fr_fr", target = "cr_elementEl_nom_long_fr_fr")
    @Mapping(source = "cr_systeme.id", target = "cr_systemeId")
    @Mapping(source = "cr_systeme.sy_nom_fr_fr", target = "cr_systemeSy_nom_fr_fr")
    Cr_systeme_elementDTO cr_systeme_elementToCr_systeme_elementDTO(Cr_systeme_element cr_systeme_element);

    @Mapping(source = "cr_elementId", target = "cr_element")
    @Mapping(source = "cr_systemeId", target = "cr_systeme")
    Cr_systeme_element cr_systeme_elementDTOToCr_systeme_element(Cr_systeme_elementDTO cr_systeme_elementDTO);

    default Cr_element cr_elementFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_element cr_element = new Cr_element();
        cr_element.setId(id);
        return cr_element;
    }

    default Cr_systeme cr_systemeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_systeme cr_systeme = new Cr_systeme();
        cr_systeme.setId(id);
        return cr_systeme;
    }
}
