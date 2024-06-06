package com.mg.vokye.model;

import com.mg.vokye.bdd.GenBDD;
import com.mg.vokye.bdd.annotation.Colonne;
import com.mg.vokye.bdd.annotation.Table;


@Table(nom = "point_vente")
public class PointVente extends GenBDD {
    @Colonne(nom = "id_point_vente")
    int idPointVente;;
    @Colonne(nom = "localisation")
    String localisation;

    public PointVente(int idPointVente, String localisation) {
        this.idPointVente = idPointVente;
        this.localisation = localisation;
    }

    public PointVente(int idPointVente) {
        this.idPointVente = idPointVente;
    }

    public PointVente() {}

    public int getIdPointVente() {
        return this.idPointVente;
    }

    public void setIdPointVente(int idPointVente) {
        this.idPointVente = idPointVente;
    }

    public String getLocalisation() {
        return this.localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    
}
