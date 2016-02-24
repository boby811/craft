package com.ju.craft.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_categorie_craft entity.
 */
public class Cr_categorie_craftDTO implements Serializable {

    private Long id;

    private String cc_nom_court_fr_fr;

    @NotNull
    private String cc_nom_long_fr_fr;

    private Set<Cr_imageDTO> cr_images = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCc_nom_court_fr_fr() {
        return cc_nom_court_fr_fr;
    }

    public void setCc_nom_court_fr_fr(String cc_nom_court_fr_fr) {
        this.cc_nom_court_fr_fr = cc_nom_court_fr_fr;
    }

    public String getCc_nom_long_fr_fr() {
        return cc_nom_long_fr_fr;
    }

    public void setCc_nom_long_fr_fr(String cc_nom_long_fr_fr) {
        this.cc_nom_long_fr_fr = cc_nom_long_fr_fr;
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

        Cr_categorie_craftDTO cr_categorie_craftDTO = (Cr_categorie_craftDTO) o;

        if ( ! Objects.equals(id, cr_categorie_craftDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_categorie_craftDTO{" +
            "id=" + id +
            ", cc_nom_court_fr_fr='" + cc_nom_court_fr_fr + "'" +
            ", cc_nom_long_fr_fr='" + cc_nom_long_fr_fr + "'" +
            '}';
    }
}
