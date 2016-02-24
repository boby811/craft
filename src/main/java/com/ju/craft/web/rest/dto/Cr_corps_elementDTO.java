package com.ju.craft.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_corps_element entity.
 */
public class Cr_corps_elementDTO implements Serializable {

    private Long id;

    private String ce_quantite;

    private Long cr_corpsId;

    private String cr_corpsCo_nom_fr_fr;

    private Long cr_elementId;

    private String cr_elementEl_nom_long_fr_fr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCe_quantite() {
        return ce_quantite;
    }

    public void setCe_quantite(String ce_quantite) {
        this.ce_quantite = ce_quantite;
    }

    public Long getCr_corpsId() {
        return cr_corpsId;
    }

    public void setCr_corpsId(Long cr_corpsId) {
        this.cr_corpsId = cr_corpsId;
    }

    public String getCr_corpsCo_nom_fr_fr() {
        return cr_corpsCo_nom_fr_fr;
    }

    public void setCr_corpsCo_nom_fr_fr(String cr_corpsCo_nom_fr_fr) {
        this.cr_corpsCo_nom_fr_fr = cr_corpsCo_nom_fr_fr;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cr_corps_elementDTO cr_corps_elementDTO = (Cr_corps_elementDTO) o;

        if ( ! Objects.equals(id, cr_corps_elementDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_corps_elementDTO{" +
            "id=" + id +
            ", ce_quantite='" + ce_quantite + "'" +
            '}';
    }
}
