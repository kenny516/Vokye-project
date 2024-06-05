package com.mg.vokye.model;

public class Ingredient {
    int id_ingredient;
    String nom;
    Unite unite;

    public Ingredient() {}

    public Ingredient(int id_ingredient, String nom, Unite unite) {
        this.id_ingredient = id_ingredient;
        this.nom = nom;
        this.unite = unite;
    }
    public int getId_ingredient() {
        return id_ingredient;
    }
    public void setId_ingredient(int id_ingredient) {
        this.id_ingredient = id_ingredient;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Unite getUnite() {
        return unite;
    }
    public void setUnite(Unite unite) {
        this.unite = unite;
    }
}
