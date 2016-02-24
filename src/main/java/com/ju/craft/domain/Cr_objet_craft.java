package com.ju.craft.domain;

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
 * A Cr_objet_craft.
 */
@Entity
@Table(name = "cr_objet_craft")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_objet_craft")
public class Cr_objet_craft implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "oc_quantite_element", nullable = false)
    private Integer oc_quantite_element;

    @ManyToOne
    @JoinColumn(name = "cr_puissance_id")
    private Cr_puissance cr_puissance;

    @ManyToOne
    @JoinColumn(name = "cr_categorie_craft_id")
    private Cr_categorie_craft cr_categorie_craft;

    @ManyToOne
    @JoinColumn(name = "cr_element_id")
    private Cr_element cr_element;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOc_quantite_element() {
        return oc_quantite_element;
    }

    public void setOc_quantite_element(Integer oc_quantite_element) {
        this.oc_quantite_element = oc_quantite_element;
    }

    public Cr_puissance getCr_puissance() {
        return cr_puissance;
    }

    public void setCr_puissance(Cr_puissance cr_puissance) {
        this.cr_puissance = cr_puissance;
    }

    public Cr_categorie_craft getCr_categorie_craft() {
        return cr_categorie_craft;
    }

    public void setCr_categorie_craft(Cr_categorie_craft cr_categorie_craft) {
        this.cr_categorie_craft = cr_categorie_craft;
    }

    public Cr_element getCr_element() {
        return cr_element;
    }

    public void setCr_element(Cr_element cr_element) {
        this.cr_element = cr_element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cr_objet_craft cr_objet_craft = (Cr_objet_craft) o;
        return Objects.equals(id, cr_objet_craft.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_objet_craft{" +
            "id=" + id +
            ", oc_quantite_element='" + oc_quantite_element + "'" +
            '}';
    }
}
