package com.ju.craft.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_element entity.
 */
public class Cr_elementDTO implements Serializable {

    private Long id;

    @NotNull
    private String el_nom_court_fr_fr;

    @NotNull
    private String el_nom_long_fr_fr;

    private String el_description_fr_fr;

    private Integer el_num_atomique;

    private Integer el_point_fusion;

    private Long cr_rareteId;

    private String cr_rareteRa_libelle_fr_fr;

    private Set<Cr_imageDTO> cr_images = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEl_nom_court_fr_fr() {
        return el_nom_court_fr_fr;
    }

    public void setEl_nom_court_fr_fr(String el_nom_court_fr_fr) {
        this.el_nom_court_fr_fr = el_nom_court_fr_fr;
    }

    public String getEl_nom_long_fr_fr() {
        return el_nom_long_fr_fr;
    }

    public void setEl_nom_long_fr_fr(String el_nom_long_fr_fr) {
        this.el_nom_long_fr_fr = el_nom_long_fr_fr;
    }

    public String getEl_description_fr_fr() {
        return el_description_fr_fr;
    }

    public void setEl_description_fr_fr(String el_description_fr_fr) {
        this.el_description_fr_fr = el_description_fr_fr;
    }

    public Integer getEl_num_atomique() {
        return el_num_atomique;
    }

    public void setEl_num_atomique(Integer el_num_atomique) {
        this.el_num_atomique = el_num_atomique;
    }

    public Integer getEl_point_fusion() {
        return el_point_fusion;
    }

    public void setEl_point_fusion(Integer el_point_fusion) {
        this.el_point_fusion = el_point_fusion;
    }

    public Long getCr_rareteId() {
        return cr_rareteId;
    }

    public void setCr_rareteId(Long cr_rareteId) {
        this.cr_rareteId = cr_rareteId;
    }

    public String getCr_rareteRa_libelle_fr_fr() {
        return cr_rareteRa_libelle_fr_fr;
    }

    public void setCr_rareteRa_libelle_fr_fr(String cr_rareteRa_libelle_fr_fr) {
        this.cr_rareteRa_libelle_fr_fr = cr_rareteRa_libelle_fr_fr;
    }

    public Set<Cr_imageDTO> getCr_images() {
        return cr_images;
    }

    public void setCr_images(Set<Cr_imageDTO> cr_images) {
        this.cr_images = cr_images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cr_elementDTO cr_elementDTO = (Cr_elementDTO) o;

        if ( ! Objects.equals(id, cr_elementDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_elementDTO{" +
            "id=" + id +
            ", el_nom_court_fr_fr='" + el_nom_court_fr_fr + "'" +
            ", el_nom_long_fr_fr='" + el_nom_long_fr_fr + "'" +
            ", el_description_fr_fr='" + el_description_fr_fr + "'" +
            ", el_num_atomique='" + el_num_atomique + "'" +
            ", el_point_fusion='" + el_point_fusion + "'" +
            '}';
    }
}
