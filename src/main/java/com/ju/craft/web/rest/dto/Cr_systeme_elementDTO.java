package com.ju.craft.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_systeme_element entity.
 */
public class Cr_systeme_elementDTO implements Serializable {

    private Long id;

    private String se_quantite;

    private Long cr_elementId;

    private String cr_elementEl_nom_long_fr_fr;

    private Long cr_systemeId;

    private String cr_systemeSy_nom_fr_fr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSe_quantite() {
        return se_quantite;
    }

    public void setSe_quantite(String se_quantite) {
        this.se_quantite = se_quantite;
    }

    public Long getCr_elementId() {
        return cr_elementId;
    }

    public void setCr_elementId(Long cr_elementId) {
        this.cr_elementId = cr_elementId;
    }

    public String getCr_elementEl_nom_long_fr_fr() {
        return cr_elementEl_nom_long_fr_fr;
    }

    public void setCr_elementEl_nom_long_fr_fr(String cr_elementEl_nom_long_fr_fr) {
        this.cr_elementEl_nom_long_fr_fr = cr_elementEl_nom_long_fr_fr;
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

        Cr_systeme_elementDTO cr_systeme_elementDTO = (Cr_systeme_elementDTO) o;

        if ( ! Objects.equals(id, cr_systeme_elementDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_systeme_elementDTO{" +
            "id=" + id +
            ", se_quantite='" + se_quantite + "'" +
            '}';
    }
}
