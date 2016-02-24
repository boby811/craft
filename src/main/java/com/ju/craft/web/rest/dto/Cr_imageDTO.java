package com.ju.craft.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cr_image entity.
 */
public class Cr_imageDTO implements Serializable {

    private Long id;

    private String im_url;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cr_imageDTO cr_imageDTO = (Cr_imageDTO) o;

        if ( ! Objects.equals(id, cr_imageDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cr_imageDTO{" +
            "id=" + id +
            ", im_url='" + im_url + "'" +
            '}';
    }
}
