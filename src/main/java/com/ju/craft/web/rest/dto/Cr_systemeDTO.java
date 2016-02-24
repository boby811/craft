package com.ju.craft.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_systeme entity.
 */
public class Cr_systemeDTO implements Serializable {

    private Long id;

    @NotNull
    private String sy_nom_fr_fr;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSy_nom_fr_fr() {
        return sy_nom_fr_fr;
    }

    public void setSy_nom_fr_fr(String sy_nom_fr_fr) {
        this.sy_nom_fr_fr = sy_nom_fr_fr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cr_systemeDTO cr_systemeDTO = (Cr_systemeDTO) o;

        if ( ! Objects.equals(id, cr_systemeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_systemeDTO{" +
            "id=" + id +
            ", sy_nom_fr_fr='" + sy_nom_fr_fr + "'" +
            '}';
    }
}
