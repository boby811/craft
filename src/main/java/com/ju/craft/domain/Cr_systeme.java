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
 * A Cr_systeme.
 */
@Entity
@Table(name = "cr_systeme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_systeme")
public class Cr_systeme implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "sy_nom_fr_fr", nullable = false)
    private String sy_nom_fr_fr;
    
    @OneToMany(mappedBy = "cr_systeme")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_systeme_element> cr_systeme_elements = new HashSet<>();

    @OneToMany(mappedBy = "cr_systeme")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_corps> cr_corpss = new HashSet<>();

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

    public Set<Cr_systeme_element> getCr_systeme_elements() {
        return cr_systeme_elements;
    }

    public void setCr_systeme_elements(Set<Cr_systeme_element> cr_systeme_elements) {
        this.cr_systeme_elements = cr_systeme_elements;
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
        Cr_systeme cr_systeme = (Cr_systeme) o;
        if(cr_systeme.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cr_systeme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_systeme{" +
            "id=" + id +
            ", sy_nom_fr_fr='" + sy_nom_fr_fr + "'" +
            '}';
    }
}
