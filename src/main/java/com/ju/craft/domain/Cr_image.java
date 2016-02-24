package com.ju.craft.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cr_image.
 */
@Entity
@Table(name = "cr_image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_image")
public class Cr_image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "im_url")
    private String im_url;

    @ManyToMany(mappedBy = "cr_images")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_element> cr_elements = new HashSet<>();

    @ManyToMany(mappedBy = "cr_images")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_categorie_craft> cr_categorie_crafts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIm_url() {
        return im_url;
    }

    public void setIm_url(String im_url) {
        this.im_url = im_url;
    }

    public Set<Cr_element> getCr_elements() {
        return cr_elements;
    }

    public void setCr_elements(Set<Cr_element> cr_elements) {
        this.cr_elements = cr_elements;
    }

    public Set<Cr_categorie_craft> getCr_categorie_crafts() {
        return cr_categorie_crafts;
    }

    public void setCr_categorie_crafts(Set<Cr_categorie_craft> cr_categorie_crafts) {
        this.cr_categorie_crafts = cr_categorie_crafts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cr_image cr_image = (Cr_image) o;
        return Objects.equals(id, cr_image.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_image{" +
            "id=" + id +
            ", im_url='" + im_url + "'" +
            '}';
    }
}
