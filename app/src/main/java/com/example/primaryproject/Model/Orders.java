package com.example.primaryproject.Model;

public class Orders {
    String id;
    String price;
    String name;
    String image;
    String publisher;
    String publishing_year;
    String description;

    String idProduct;

    String Quantity;

    String Days;
    Long Price;

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public Orders(String id, String idProduct, String name, String image, String publisher, String publishing_year) {
        this.id = id;
        this.idProduct=idProduct;
        this.name = name;
        this.image = image;
        this.publisher = publisher;
        this.publishing_year = publishing_year;

    }

    public Orders(String id, String name, String image, String publisher, String publishing_year, String idProduct, String quantity, String days, Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.publisher = publisher;
        this.publishing_year = publishing_year;
        this.idProduct = idProduct;
        Quantity = quantity;
        Days = days;
        Price = price;
    }

    public Orders(String id, String idProduct, String name, String image, String publisher, String publishing_year, String quantity, String days) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.publisher = publisher;
        this.publishing_year = publishing_year;

        this.idProduct = idProduct;
       this.Quantity = quantity;
        this.Days = days;
    }

    public Orders(String id, String price, String name, String image, String publisher, String publishing_year, String description, String idProduct, String quantity, String days) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.image = image;
        this.publisher = publisher;
        this.publishing_year = publishing_year;
        this.description = description;
        this.idProduct = idProduct;
        Quantity = quantity;
        Days = days;
    }

//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }


    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
