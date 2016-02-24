package com.ju.craft.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_corps entity.
 */
public class Cr_corpsDTO implements Serializable {

    private Long id;

    private String co_nom_fr_fr;

    private Long cr_type_corpsId;

    private String cr_type_corpsTc_libelle_fr_fr;

    private Long cr_systemeId;

    private String cr_systemeSy_nom_fr_fr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCo_nom_fr_fr() {
        return co_nom_fr_fr;
    }

    public void setCo_nom_fr_fr(String co_nom_fr_fr) {
        this.co_nom_fr_fr = co_nom_fr_fr;
    }

    public Long getCr_type_corpsId() {
        return cr_type_corpsId;
    }

    public void setCr_type_corpsId(Long cr_type_corpsId) {
        this.cr_type_corpsId = cr_type_corpsId;
    }

    public String getCr_type_corpsTc_libelle_fr_fr() {
        return cr_type_corpsTc_libelle_fr_fr;
    }

    public void setCr_type_corpsTc_libelle_fr_fr(String cr_type_corpsTc_libelle_fr_fr) {
        this.cr_type_corpsTc_libelle_fr_fr = cr_type_corpsTc_libelle_fr_fr;
    }

    public Long getCr_systemeId() {
        return cr_systemeId;
    }

    public void setCr_systemeId(Long cr_systemeId) {
        this.cr_systemeId = cr_systemeId;
    }

    public String getCr_systemeSy_nom_fr_fr() {
        return cr_systemeSy_nom_fr_fr;
    }

    public void setCr_systemeSy_nom_fr_fr(String cr_systemeSy_nom_fr_fr) {
        this.cr_systemeSy_nom_fr_fr = cr_systemeSy_nom_fr_fr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cr_corpsDTO cr_corpsDTO = (Cr_corpsDTO) o;

        if ( ! Objects.equals(id, cr_corpsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_corpsDTO{" +
            "id=" + id +
            ", co_nom_fr_fr='" + co_nom_fr_fr + "'" +
            '}';
    }
}
