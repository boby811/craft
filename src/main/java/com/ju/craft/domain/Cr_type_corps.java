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
 * A Cr_type_corps.
 */
@Entity
@Table(name = "cr_type_corps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_type_corps")
public class Cr_type_corps implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "tc_libelle_fr_fr", nullable = false)
    private String tc_libelle_fr_fr;

    @OneToMany(mappedBy = "cr_type_corps")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_corps> cr_corpss = new HashSet<>();

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

    public Set<Cr_corps> getCr_corpss() {
        return cr_corpss;
    }

    public void setCr_corpss(Set<Cr_corps> cr_corpss) {
        this.cr_corpss = cr_corpss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cr_type_corps cr_type_corps = (Cr_type_corps) o;
        return Objects.equals(id, cr_type_corps.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_type_corps{" +
            "id=" + id +
            ", tc_libelle_fr_fr='" + tc_libelle_fr_fr + "'" +
            '}';
    }
}
