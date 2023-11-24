package org.acme.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;


@Entity
@Table(name = "minerals")
public class Metallum extends PanacheEntity {
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private String color;
    private String crystalSystem;
    private String mohsScale;
    private String formula;
    private String ultravioletFluorescence;
    private String otherProperties;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    public String getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(String otherProperties) {
        this.otherProperties = otherProperties;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCrystalSystem() {
        return crystalSystem;
    }

    public void setCrystalSystem(String crystalSystem) {
        this.crystalSystem = crystalSystem;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getUltravioletFluorescence() {
        return ultravioletFluorescence;
    }

    public void setUltravioletFluorescence(String cbd) {
        this.ultravioletFluorescence = cbd;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMohsScale() {
        return mohsScale;
    }

    public void setMohsScale(String mohsScale) {
        this.mohsScale = mohsScale;
    }
}
