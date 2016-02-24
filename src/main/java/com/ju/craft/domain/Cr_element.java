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
 * A Cr_element.
 */
@Entity
@Table(name = "cr_element")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_element")
public class Cr_element implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "el_nom_court_fr_fr", nullable = false)
    private String el_nom_court_fr_fr;

    @NotNull
    @Column(name = "el_nom_long_fr_fr", nullable = false)
    private String el_nom_long_fr_fr;

    @Column(name = "el_description_fr_fr")
    private String el_description_fr_fr;

    @Column(name = "el_num_atomique")
    private Integer el_num_atomique;

    @Column(name = "el_point_fusion")
    private Integer el_point_fusion;

    @ManyToOne
    @JoinColumn(name = "cr_rarete_id")
    private Cr_rarete cr_rarete;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cr_element_cr_image",
               joinColumns = @JoinColumn(name="cr_elements_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="cr_images_id", referencedColumnName="ID"))
    private Set<Cr_image> cr_images = new HashSet<>();

    @OneToMany(mappedBy = "cr_element")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_corps_element> cr_corps_elements = new HashSet<>();

    @OneToMany(mappedBy = "cr_element")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_objet_craft> cr_objet_crafts = new HashSet<>();

    @OneToMany(mappedBy = "cr_element")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_systeme_element> cr_systeme_elements = new HashSet<>();

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

    public Cr_rarete getCr_rarete() {
        return cr_rarete;
    }

    public void setCr_rarete(Cr_rarete cr_rarete) {
        this.cr_rarete = cr_rarete;
    }

    public Set<Cr_image> getCr_images() {
        return cr_images;
    }

    public void setCr_images(Set<Cr_image> cr_images) {
        this.cr_images = cr_images;
    }

    public Set<Cr_corps_element> getCr_corps_elements() {
        return cr_corps_elements;
    }

    public void setCr_corps_elements(Set<Cr_corps_element> cr_corps_elements) {
        this.cr_corps_elements = cr_corps_elements;
    }

    public Set<Cr_objet_craft> getCr_objet_crafts() {
        return cr_objet_crafts;
    }

    public void setCr_objet_crafts(Set<Cr_objet_craft> cr_objet_crafts) {
        this.cr_objet_crafts = cr_objet_crafts;
    }

    public Set<Cr_systeme_element> getCr_systeme_elements() {
        return cr_systeme_elements;
    }

    public void setCr_systeme_elements(Set<Cr_systeme_element> cr_systeme_elements) {
        this.cr_systeme_elements = cr_systeme_elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cr_element cr_element = (Cr_element) o;
        return Objects.equals(id, cr_element.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_element{" +
            "id=" + id +
            ", el_nom_court_fr_fr='" + el_nom_court_fr_fr + "'" +
            ", el_nom_long_fr_fr='" + el_nom_long_fr_fr + "'" +
            ", el_description_fr_fr='" + el_description_fr_fr + "'" +
            ", el_num_atomique='" + el_num_atomique + "'" +
            ", el_point_fusion='" + el_point_fusion + "'" +
            '}';
    }
}
