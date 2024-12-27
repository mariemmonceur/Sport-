package com.example.projet_pfa1.Models;

public class CommandeModel {

    String id_commande;
    String id_produit;
    String id_acheteur;
    String nom_produit;
    String prix;
    String quantité;
    String image;
    String id_vendeur;
    String id_categorie;
    String date_commande;
    String adresse;

    public CommandeModel(String id_commande, String id_produit, String id_acheteur, String nom_produit, String prix, String quantité, String image, String id_vendeur, String id_categorie, String date_commande, String adresse) {
        this.id_commande = id_commande;
        this.id_produit = id_produit;
        this.id_acheteur = id_acheteur;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.quantité = quantité;
        this.image = image;
        this.id_vendeur = id_vendeur;
        this.id_categorie = id_categorie;
        this.date_commande = date_commande;
        this.adresse = adresse;
    }

    public String getId_commande() {
        return id_commande;
    }

    public void setId_commande(String id_commande) {
        this.id_commande = id_commande;
    }

    public String getId_produit() {
        return id_produit;
    }

    public void setId_produit(String id_produit) {
        this.id_produit = id_produit;
    }

    public String getId_acheteur() {
        return id_acheteur;
    }

    public void setId_acheteur(String id_acheteur) {
        this.id_acheteur = id_acheteur;
    }

    public String getNom_produit() {
        return nom_produit;
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

    public String getQuantité() {
        return quantité;
    }

    public void setQuantité(String quantité) {
        this.quantité = quantité;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_vendeur() {
        return id_vendeur;
    }

    public void setId_vendeur(String id_vendeur) {
        this.id_vendeur = id_vendeur;
    }

    public String getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(String id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
