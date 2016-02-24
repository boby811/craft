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
 * A Cr_categorie_craft.
 */
@Entity
@Table(name = "cr_categorie_craft")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_categorie_craft")
public class Cr_categorie_craft implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cc_nom_court_fr_fr")
    private String cc_nom_court_fr_fr;

    @NotNull
    @Column(name = "cc_nom_long_fr_fr", nullable = false)
    private String cc_nom_long_fr_fr;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cr_categorie_craft_cr_image",
               joinColumns = @JoinColumn(name="cr_categorie_crafts_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="cr_images_id", referencedColumnName="ID"))
    private Set<Cr_image> cr_images = new HashSet<>();

    @OneToMany(mappedBy = "cr_categorie_craft")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_objet_craft> cr_objet_crafts = new HashSet<>();

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

    public Set<Cr_image> getCr_images() {
        return cr_images;
    }

    public void setCr_images(Set<Cr_image> cr_images) {
        this.cr_images = cr_images;
    }

    public Set<Cr_objet_craft> getCr_objet_crafts() {
        return cr_objet_crafts;
    }

    public void setCr_objet_crafts(Set<Cr_objet_craft> cr_objet_crafts) {
        this.cr_objet_crafts = cr_objet_crafts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cr_categorie_craft cr_categorie_craft = (Cr_categorie_craft) o;
        return Objects.equals(id, cr_categorie_craft.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_categorie_craft{" +
            "id=" + id +
            ", cc_nom_court_fr_fr='" + cc_nom_court_fr_fr + "'" +
            ", cc_nom_long_fr_fr='" + cc_nom_long_fr_fr + "'" +
            '}';
    }
}
