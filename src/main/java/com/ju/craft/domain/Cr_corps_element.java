package com.ju.craft.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cr_corps_element.
 */
@Entity
@Table(name = "cr_corps_element")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_corps_element")
public class Cr_corps_element implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ce_quantite")
    private String ce_quantite;

    @ManyToOne
    @JoinColumn(name = "cr_corps_id")
    private Cr_corps cr_corps;

    @ManyToOne
    @JoinColumn(name = "cr_element_id")
    private Cr_element cr_element;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCe_quantite() {
        return ce_quantite;
    }

    public void setCe_quantite(String ce_quantite) {
        this.ce_quantite = ce_quantite;
    }

    public Cr_corps getCr_corps() {
        return cr_corps;
    }

    public void setCr_corps(Cr_corps cr_corps) {
        this.cr_corps = cr_corps;
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
        Cr_corps_element cr_corps_element = (Cr_corps_element) o;
        return Objects.equals(id, cr_corps_element.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_corps_element{" +
            "id=" + id +
            ", ce_quantite='" + ce_quantite + "'" +
            '}';
    }
}
