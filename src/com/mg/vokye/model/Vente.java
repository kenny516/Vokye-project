package com.mg.vokye.model;

import com.mg.vokye.bdd.GenBDD;
import com.mg.vokye.bdd.annotation.Colonne;
import com.mg.vokye.bdd.annotation.Table;

import java.time.LocalDate;

@Table(nom = "vente")
public class Vente extends GenBDD {
    @Colonne(nom = "id_vente")
    int id_vente;
    @Colonne(nom = "id_chariot")
    int id_chariot;
    @Colonne(nom = "id_produit")
    int id_produit;
    @Colonne(nom = "quantite")
    int quantite;
    @Colonne(nom = "date_vente")
    LocalDate date_vente;

    public int getId_vente() {
        return id_vente;
    }

    public void setId_vente(int id_vente) {
        this.id_vente = id_vente;
    }

    public int getId_chariot() {
        return id_chariot;
    }

    public void setId_chariot(int id_chariot) {
        this.id_chariot = id_chariot;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDate_vente() {
        return date_vente;
    }

    public void setDate_vente(LocalDate date_vente) {
        this.date_vente = date_vente;
    }
}
