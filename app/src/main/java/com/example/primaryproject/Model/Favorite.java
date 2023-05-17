package com.example.primaryproject.Model;

import java.io.Serializable;

public class Favorite implements Serializable {
    String id;
    String idproduct;
    String name;
    String image;
    String publisher;
    String publishing_year;
    String description;

    public Favorite(String id,String idproduct, String name, String image, String publisher, String publishing_year) {

        this.id=id;
        this.idproduct = idproduct;
        this.name = name;
        this.image = image;
        this.publisher = publisher;
        this.publishing_year = publishing_year;
    }

    public String getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishing_year() {
        return publishing_year;
    }

    public void setPublishing_year(String publishing_year) {
        this.publishing_year = publishing_year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
