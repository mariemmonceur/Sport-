package com.example.projet_pfa1.Models;

public class MonPanierModel {

    String nom_produit;
    String prix;

    String id_produit;
    String temps;
    String date;
    String quantité;

    String img;

    public MonPanierModel() {

    }

    public MonPanierModel(String nom_produit, String prix, String id_produit, String temps, String date, String quantité,String img) {
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.id_produit = id_produit;
        this.temps = temps;
        this.date = date;
        this.quantité = quantité;
        this.img=img;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getId_produit() {
        return id_produit;
    }

    public void setId_produit(String id_produit) {
        this.id_produit = id_produit;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantité() {
        return quantité;
    }

    public void setQuantité(String quantité) {
        this.quantité = quantité;
    }
}
