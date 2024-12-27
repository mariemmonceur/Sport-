package com.example.projet_pfa1.Models;

public class CategoryModel {

    String img_url;
    String name;
    String id_cat;

    public CategoryModel(String img_url, String name, String cat) {
        this.img_url = img_url;
        this.name = name;
        this.id_cat = cat;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_cat() {
        return id_cat;
    }

    public void setId_cat(String id_cat) {
        this.id_cat = id_cat;
    }
}
