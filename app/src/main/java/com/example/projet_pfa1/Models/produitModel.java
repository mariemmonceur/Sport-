package com.example.projet_pfa1.Models;

public class produitModel {

   String  name;
   String prix;
   String img;
   String id_prod;

   public produitModel(String name, String prix, String img, String id_prod) {
      this.name = name;
      this.prix = prix;
      this.img = img;
      this.id_prod = id_prod;
   }

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
}
