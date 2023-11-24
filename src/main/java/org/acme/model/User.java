package org.acme.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends PanacheEntity{

    private String username;
    private String password;
    private String role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Metallum> metallums;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Metallum> getMetallum() {
        return metallums;
    }

    public void setMetallum(Metallum metallum) {
        if (metallums == null) {
            metallums = new ArrayList<>();
        }
        metallums.add(metallum);
        metallum.setUser(this);
    }
    public void setMetallum() {
        if (metallums == null) {
            metallums = new ArrayList<>();
        }

    }
}

