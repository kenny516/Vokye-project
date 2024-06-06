package com.mg.vokye.model;

public class Produit {
    int idProduit;
    String nomProduit;
    double prix;

    public Produit() {}

    public Produit(int idProduit, String nomProduit, double prix) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.prix = prix;
    }

    // getters
    public int getIdProduit() {
        return idProduit;
    }
    public String getNomProduit() {
        return nomProduit;
    }
    public double getPrix() {
        return prix;
    }

    // setters
    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }
    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
}
