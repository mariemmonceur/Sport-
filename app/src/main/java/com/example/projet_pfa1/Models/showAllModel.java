package com.example.projet_pfa1.Models;

public class showAllModel {

    String  name;
    String prix;
    String img;
    String id_prod;

    String marque ;
    String nomcategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId_prod() {
        return id_prod;
    }

    public void setId_prod(String id_prod) {
        this.id_prod = id_prod;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNomcategory() {
        return nomcategory;
    }

    public void setNomcategory(String nomcategory) {
        this.nomcategory = nomcategory;
    }

    public showAllModel(String name, String prix, String img, String id_prod, String marque, String nomcategory) {
        this.name = name;
        this.prix = prix;
        this.img = img;
        this.id_prod = id_prod;
        this.marque = marque;
        this.nomcategory = nomcategory;


    }
}
