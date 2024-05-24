package Model;

import java.sql.Date;
import bdd.annotation.*;
import bdd.*;

@Table(nom="Depense")
public class Depense extends GenBDD {
    @Colonne(nom="id_depense")
    int id_depense ;
    @Colonne(nom="id_type_depense")
    int id_type_depense ;
    @Colonne(nom="prix")
    double prix;
    @Colonne(nom="date_depense")
    Date date_depense; 

    //Setter and getter
    ///id_depense
    public int getId_depense(){
        return id_depense;
    }
    public void setId_depense(int id_depense){
        this.id_depense = id_depense;
    }

    ///id_type_depense
    public int getId_type_depense(){
        return this.id_type_depense;
    }
    public void setId_type_depense(int id_type_dep){
        this.id_type_depense = id_type_dep;
    }

    ///prix 
    public double getPrix(){
        return this.prix;
    }
    public void setPrix(double nPrix){
        this.prix = nPrix;
    }

    ///Date_depense
    public Date getDate_depense(){
        return this.date_depense;
    }
    public void setDate_depense(Date date_depense){
        this.date_depense = date_depense;
    }
}