package com.ju.craft.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_rarete entity.
 */
public class Cr_rareteDTO implements Serializable {

    private Long id;

    @NotNull
    private String ra_libelle_fr_fr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRa_libelle_fr_fr() {
        return ra_libelle_fr_fr;
    }

    public void setRa_libelle_fr_fr(String ra_libelle_fr_fr) {
        this.ra_libelle_fr_fr = ra_libelle_fr_fr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cr_rareteDTO cr_rareteDTO = (Cr_rareteDTO) o;

        if ( ! Objects.equals(id, cr_rareteDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_rareteDTO{" +
            "id=" + id +
            ", ra_libelle_fr_fr='" + ra_libelle_fr_fr + "'" +
            '}';
    }
}
