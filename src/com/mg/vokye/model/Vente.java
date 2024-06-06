package com.mg.vokye.model;

import com.mg.vokye.bdd.GenBDD;
import com.mg.vokye.bdd.annotation.Colonne;
import com.mg.vokye.bdd.annotation.Table;

import java.util.Date;

@Table(nom = "vente")
public class Vente extends GenBDD {
    @Colonne(nom = "id_vente")
    int id_vente;
    @Colonne(nom = "id_point_vente")
    int id_point_vente;
    @Colonne(nom = "id_chariot")
    int id_chariot;
    @Colonne(nom = "id_produit")
    int id_produit;
    @Colonne(nom = "quantite")
    int quantite;
    @Colonne(nom = "date_vente")
    Date date_vente;

    public int getId_vente() {
        return id_vente;
    }

    public void setId_vente(int id_vente) {
        this.id_vente = id_vente;
    }

    public int getId_point_vente() {
        return id_point_vente;
    }

    public void setId_point_vente(int id_point_vente) {
        this.id_point_vente = id_point_vente;
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

    public Date getDate_vente() {
        return date_vente;
    }

    public void setDate_vente(Object date_vente) {
        if (date_vente instanceof java.sql.Date) {
            this.date_vente = new java.util.Date(((java.sql.Date) date_vente).getTime());
        } else if (date_vente instanceof java.util.Date) {
            this.date_vente = (java.util.Date) date_vente;
        } else {
            throw new IllegalArgumentException("Unsupported date type");
        }
    }

}
