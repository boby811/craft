package com.ju.craft.web.rest.mapper;

import com.ju.craft.domain.*;
import com.ju.craft.web.rest.dto.Cr_corpsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cr_corps and its DTO Cr_corpsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Cr_corpsMapper {

    @Mapping(source = "cr_type_corps.id", target = "cr_type_corpsId")
    @Mapping(source = "cr_type_corps.tc_libelle_fr_fr", target = "cr_type_corpsTc_libelle_fr_fr")
    @Mapping(source = "cr_systeme.id", target = "cr_systemeId")
    @Mapping(source = "cr_systeme.sy_nom_fr_fr", target = "cr_systemeSy_nom_fr_fr")
    Cr_corpsDTO cr_corpsToCr_corpsDTO(Cr_corps cr_corps);

    @Mapping(target = "cr_corps_elements", ignore = true)
    @Mapping(source = "cr_type_corpsId", target = "cr_type_corps")
    @Mapping(source = "cr_systemeId", target = "cr_systeme")
    Cr_corps cr_corpsDTOToCr_corps(Cr_corpsDTO cr_corpsDTO);

    default Cr_type_corps cr_type_corpsFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cr_type_corps cr_type_corps = new Cr_type_corps();
        cr_type_corps.setId(id);
        return cr_type_corps;
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
