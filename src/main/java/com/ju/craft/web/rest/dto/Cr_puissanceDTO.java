package com.ju.craft.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_puissance entity.
 */
public class Cr_puissanceDTO implements Serializable {

    private Long id;

    @NotNull
    private String pu_libelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPu_libelle() {
        return pu_libelle;
    }

    public void setPu_libelle(String pu_libelle) {
        this.pu_libelle = pu_libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cr_puissanceDTO cr_puissanceDTO = (Cr_puissanceDTO) o;

        if ( ! Objects.equals(id, cr_puissanceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_puissanceDTO{" +
            "id=" + id +
            ", pu_libelle='" + pu_libelle + "'" +
            '}';
    }
}
