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
 * A Cr_puissance.
 */
@Entity
@Table(name = "cr_puissance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cr_puissance")
public class Cr_puissance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "pu_libelle", nullable = false)
    private String pu_libelle;

    @OneToMany(mappedBy = "cr_puissance")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cr_objet_craft> cr_objet_crafts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPu_libelle() {
        return pu_libelle;
    }

    public void setPu_libelle(String pu_libelle) {
        this.pu_libelle = pu_libelle;
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
        Cr_puissance cr_puissance = (Cr_puissance) o;
        return Objects.equals(id, cr_puissance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_puissance{" +
            "id=" + id +
            ", pu_libelle='" + pu_libelle + "'" +
            '}';
    }
}
