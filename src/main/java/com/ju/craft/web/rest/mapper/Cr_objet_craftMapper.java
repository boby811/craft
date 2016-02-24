package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_objet_craftDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_objet_craft and its DTO Cr_objet_craftDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_objet_craftMapper {

    @Mapping(source = "cr_puissance.id", target = "cr_puissanceId")
    @Mapping(source = "cr_puissance.pu_libelle", target = "cr_puissancePu_libelle")
    @Mapping(source = "cr_categorie_craft.id", target = "cr_categorie_craftId")
    @Mapping(source = "cr_categorie_craft.cc_nom_long_fr_fr", target = "cr_categorie_craftCc_nom_long_fr_fr")
    @Mapping(source = "cr_element.id", target = "cr_elementId")
    @Mapping(source = "cr_element.el_nom_long_fr_fr", target = "cr_elementEl_nom_long_fr_fr")
    Cr_objet_craftDTO cr_objet_craftToCr_objet_craftDTO(Cr_objet_craft cr_objet_craft);

    @Mapping(source = "cr_puissanceId", target = "cr_puissance")
    @Mapping(source = "cr_categorie_craftId", target = "cr_categorie_craft")
    @Mapping(source = "cr_elementId", target = "cr_element")
    Cr_objet_craft cr_objet_craftDTOToCr_objet_craft(Cr_objet_craftDTO cr_objet_craftDTO);

    default Cr_puissance cr_puissanceFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_puissance cr_puissance = new Cr_puissance();
        cr_puissance.setId(id);
        return cr_puissance;
    }

    default Cr_categorie_craft cr_categorie_craftFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_categorie_craft cr_categorie_craft = new Cr_categorie_craft();
        cr_categorie_craft.setId(id);
        return cr_categorie_craft;
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
