package com.ju.craft.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_objet_craft entity.
 */
public class Cr_objet_craftDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer oc_quantite_element;

    private Long cr_puissanceId;

    private String cr_puissancePu_libelle;

    private Long cr_categorie_craftId;

    private String cr_categorie_craftCc_nom_long_fr_fr;

    private Long cr_elementId;

    private String cr_elementEl_nom_long_fr_fr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOc_quantite_element() {
        return oc_quantite_element;
    }

    public void setOc_quantite_element(Integer oc_quantite_element) {
        this.oc_quantite_element = oc_quantite_element;
    }

    public Long getCr_puissanceId() {
        return cr_puissanceId;
    }

    public void setCr_puissanceId(Long cr_puissanceId) {
        this.cr_puissanceId = cr_puissanceId;
    }

    public String getCr_puissancePu_libelle() {
        return cr_puissancePu_libelle;
    }

    public void setCr_puissancePu_libelle(String cr_puissancePu_libelle) {
        this.cr_puissancePu_libelle = cr_puissancePu_libelle;
    }

    public Long getCr_categorie_craftId() {
        return cr_categorie_craftId;
    }

    public void setCr_categorie_craftId(Long cr_categorie_craftId) {
        this.cr_categorie_craftId = cr_categorie_craftId;
    }

    public String getCr_categorie_craftCc_nom_long_fr_fr() {
        return cr_categorie_craftCc_nom_long_fr_fr;
    }

    public void setCr_categorie_craftCc_nom_long_fr_fr(String cr_categorie_craftCc_nom_long_fr_fr) {
        this.cr_categorie_craftCc_nom_long_fr_fr = cr_categorie_craftCc_nom_long_fr_fr;
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

        Cr_objet_craftDTO cr_objet_craftDTO = (Cr_objet_craftDTO) o;

        if ( ! Objects.equals(id, cr_objet_craftDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_objet_craftDTO{" +
            "id=" + id +
            ", oc_quantite_element='" + oc_quantite_element + "'" +
            '}';
    }
}
