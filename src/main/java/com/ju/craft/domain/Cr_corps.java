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
 * A Cr_corps.
 */
@Entity
@Table(name = "cr_corps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_corps")
public class Cr_corps implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "co_nom_fr_fr")
    private String co_nom_fr_fr;

    @OneToMany(mappedBy = "cr_corps")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_corps_element> cr_corps_elements = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "cr_type_corps_id")
    private Cr_type_corps cr_type_corps;

    @ManyToOne
    @JoinColumn(name = "cr_systeme_id")
    private Cr_systeme cr_systeme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCo_nom_fr_fr() {
        return co_nom_fr_fr;
    }

    public void setCo_nom_fr_fr(String co_nom_fr_fr) {
        this.co_nom_fr_fr = co_nom_fr_fr;
    }

    public Set<Cr_corps_element> getCr_corps_elements() {
        return cr_corps_elements;
    }

    public void setCr_corps_elements(Set<Cr_corps_element> cr_corps_elements) {
        this.cr_corps_elements = cr_corps_elements;
    }

    public Cr_type_corps getCr_type_corps() {
        return cr_type_corps;
    }

    public void setCr_type_corps(Cr_type_corps cr_type_corps) {
        this.cr_type_corps = cr_type_corps;
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
        Cr_corps cr_corps = (Cr_corps) o;
        return Objects.equals(id, cr_corps.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_corps{" +
            "id=" + id +
            ", co_nom_fr_fr='" + co_nom_fr_fr + "'" +
            '}';
    }
}
