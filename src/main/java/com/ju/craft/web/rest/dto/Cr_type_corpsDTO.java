package com.ju.craft.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_type_corps entity.
 */
public class Cr_type_corpsDTO implements Serializable {

    private Long id;

    @NotNull
    private String tc_libelle_fr_fr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTc_libelle_fr_fr() {
        return tc_libelle_fr_fr;
    }

    public void setTc_libelle_fr_fr(String tc_libelle_fr_fr) {
        this.tc_libelle_fr_fr = tc_libelle_fr_fr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cr_type_corpsDTO cr_type_corpsDTO = (Cr_type_corpsDTO) o;

        if ( ! Objects.equals(id, cr_type_corpsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_type_corpsDTO{" +
            "id=" + id +
            ", tc_libelle_fr_fr='" + tc_libelle_fr_fr + "'" +
            '}';
    }
}
