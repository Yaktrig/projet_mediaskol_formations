
package fr.mediaskol.projet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SessionFormationDistanciel {

    @Id
    private Long id;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}