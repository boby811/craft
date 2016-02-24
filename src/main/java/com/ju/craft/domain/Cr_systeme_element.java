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
 * A Cr_systeme_element.
 */
@Entity
@Table(name = "cr_systeme_element")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_systeme_element")
public class Cr_systeme_element implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "se_quantite")
    private String se_quantite;

    @ManyToOne
    @JoinColumn(name = "cr_element_id")
    private Cr_element cr_element;

    @ManyToOne
    @JoinColumn(name = "cr_systeme_id")
    private Cr_systeme cr_systeme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSe_quantite() {
        return se_quantite;
    }

    public void setSe_quantite(String se_quantite) {
        this.se_quantite = se_quantite;
    }

    public Cr_element getCr_element() {
        return cr_element;
    }

    public void setCr_element(Cr_element cr_element) {
        this.cr_element = cr_element;
    }

    public Cr_systeme getCr_systeme() {
        return cr_systeme;
    }

    public void setCr_systeme(Cr_systeme cr_systeme) {
        this.cr_systeme = cr_systeme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cr_systeme_element cr_systeme_element = (Cr_systeme_element) o;
        return Objects.equals(id, cr_systeme_element.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_systeme_element{" +
            "id=" + id +
            ", se_quantite='" + se_quantite + "'" +
            '}';
    }
}
