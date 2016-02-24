package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_elementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_element and its DTO Cr_elementDTO.
 */
@Mapper(componentModel = "spring", uses = {Cr_imageMapper.class, })
public interface Cr_elementMapper {

    @Mapping(source = "cr_rarete.id", target = "cr_rareteId")
    @Mapping(source = "cr_rarete.ra_libelle_fr_fr", target = "cr_rareteRa_libelle_fr_fr")
    Cr_elementDTO cr_elementToCr_elementDTO(Cr_element cr_element);

    @Mapping(source = "cr_rareteId", target = "cr_rarete")
    @Mapping(target = "cr_corps_elements", ignore = true)
    @Mapping(target = "cr_objet_crafts", ignore = true)
    @Mapping(target = "cr_systeme_elements", ignore = true)
    Cr_element cr_elementDTOToCr_element(Cr_elementDTO cr_elementDTO);

    default Cr_rarete cr_rareteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_rarete cr_rarete = new Cr_rarete();
        cr_rarete.setId(id);
        return cr_rarete;
    }

    default Cr_image cr_imageFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_image cr_image = new Cr_image();
        cr_image.setId(id);
        return cr_image;
    }
}
