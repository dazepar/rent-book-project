package com.example.primaryproject.Model;

import java.io.Serializable;

public class Category implements Serializable {

    String id;
    String idcate;
    String idproduct;
    String name;
    String image;
    String publisher;
    String publishing_year;
    String description;


    public Category(String id, String idcate, String idproduct, String name, String image) {
        this.id = id;
        this.idcate = idcate;
        this.idproduct = idproduct;
        this.name = name;
        this.image = image;
    }

    public Category(String id, String idcate, String idproduct, String name, String image, String publisher, String publishing_year, String description) {
        this.id = id;
        this.idcate = idcate;
        this.idproduct = idproduct;
        this.name = name;
        this.image = image;
        this.publisher = publisher;
        this.publishing_year = publishing_year;
        this.description = description;
    }

    public Category(String id, String idcate, String name, String image) {
        this.id = id;
        this.idcate = idcate;
        this.name = name;
        this.image = image;
    }

    public Category(String idcate, String name, String image) {
        this.idcate = idcate;
        this.name = name;
        this.image = image;
    }
    public Category(String image) {
        this.image = image;
    }

    public Category(String name, String idcate) {

        this.name = name;
        this.idcate = idcate;

    }

    public String getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getIdcate() {
        return idcate;
    }

    public void setIdcate(String idcate) {
        this.idcate = idcate;
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


    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", idcate='" + idcate + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
