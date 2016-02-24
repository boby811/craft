package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_corps_elementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_corps_element and its DTO Cr_corps_elementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_corps_elementMapper {

    @Mapping(source = "cr_corps.id", target = "cr_corpsId")
    @Mapping(source = "cr_corps.co_nom_fr_fr", target = "cr_corpsCo_nom_fr_fr")
    @Mapping(source = "cr_element.id", target = "cr_elementId")
    @Mapping(source = "cr_element.el_nom_long_fr_fr", target = "cr_elementEl_nom_long_fr_fr")
    Cr_corps_elementDTO cr_corps_elementToCr_corps_elementDTO(Cr_corps_element cr_corps_element);

    @Mapping(source = "cr_corpsId", target = "cr_corps")
    @Mapping(source = "cr_elementId", target = "cr_element")
    Cr_corps_element cr_corps_elementDTOToCr_corps_element(Cr_corps_elementDTO cr_corps_elementDTO);

    default Cr_corps cr_corpsFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_corps cr_corps = new Cr_corps();
        cr_corps.setId(id);
        return cr_corps;
    }

    default Cr_element cr_elementFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_element cr_element = new Cr_element();
        cr_element.setId(id);
        return cr_element;
    }
}
