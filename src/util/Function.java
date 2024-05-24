package util;

import model.Depense;
import model.Vente;
import model.Prix;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import bdd.annotation.Colonne;
import bdd.annotation.Table;

public class Function {
    //function pour depense
    public double total_depense(Connection connex,Date d1,Date d2) throws Exception {
        //Initialisation de tout les variables que l'on va utiliser
        Date date_last, date_first ;    //les deux date pour eviter les ereurs 
        double total = 0;               //total dépense
        //Pour eviter les erreurs : aus cas où les deux dates sont inversé
        if(d1.compareTo(d2) > 0 ){  //si la date d1 est supérieur à la date d2
            date_first = d1;
            date_last = d2;
        }else {
            date_first = d2;
            date_last = d1;
        }

        //Pour getLa somme 
        Connection co = null;
        Statement state = null;

        try {
            co = connex;
            String sql = "SELECT * FROM depense where date_depense > "+date_first+" and date_depense < "+date_last ;
            state = co.createStatement();

            ResultSet res = state.executeQuery(sql);
            while (res.next()) {
                total += res.getDouble("id_prix");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
                if (co != null) {
                    co.close();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
        
        return total;
    }

    //Function pour vente
    public double total_vente(Connection connex,Date d1,Date d2) throws Exception {
        //Initialisation de tout les variables que l'on va utiliser
        Date date_last, date_first ;    //les deux date pour eviter les ereurs 
        double total = 0;               //total dépense
        //Pour eviter les erreurs : aus cas où les deux dates sont inversé
        if(d1.compareTo(d2) > 0 ){  //si la date d1 est supérieur à la date d2
            date_first = d1;
            date_last = d2;
        }else {
            date_first = d2;
            date_last = d1;
        }

        //Pour getLa somme 
        Connection co = null;
        Statement state = null;

        try {
            co = connex;
            String sql = "SELECT * FROM vente where date_vente > "+date_first+" and date_vente < "+date_last ;
            state = co.createStatement();

            ResultSet res = state.executeQuery(sql);
            while (res.next()) {
                total += this.getPrixProduit(res.getDouble("id_produit")) * res.getInt("quantite");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
                if (co != null) {
                    co.close();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
        
        return total;
    }

    public double getPrixProduit(Connection connex,int id_produit){
        Produit pd = new Produit();
        pd.getById(connex,id_produit);
        return pd.getPrix();
    }

    //Function chiffre affaire 
    public double chiffre_affaire(Connection connex, Date d1,Date d2) throws Exception{
        double chiffre_affaire = 0; 
        try{
            chiffre_affaire = this.total_depense(connex, d1, d2) - this.total_vente(connex, d1, d2);
        }catch(Exception e){
            throw e ;
        }
        return chiffre_affaire;
    }

}
