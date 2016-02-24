package com.ju.craft.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cr_rarete.
 */
@Entity
@Table(name = "cr_rarete")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_rarete")
public class Cr_rarete implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ra_libelle_fr_fr", nullable = false)
    private String ra_libelle_fr_fr;

    @OneToMany(mappedBy = "cr_rarete")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_element> cr_elements = new HashSet<>();

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

    public Set<Cr_element> getCr_elements() {
        return cr_elements;
    }

    public void setCr_elements(Set<Cr_element> cr_elements) {
        this.cr_elements = cr_elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cr_rarete cr_rarete = (Cr_rarete) o;
        return Objects.equals(id, cr_rarete.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_rarete{" +
            "id=" + id +
            ", ra_libelle_fr_fr='" + ra_libelle_fr_fr + "'" +
            '}';
    }
}
