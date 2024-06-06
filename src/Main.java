import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.model.Vente;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args)  {
        Vente vt = new Vente();
        Connexion connex = new Connexion();
        try{
            List<Object> ventes =  vt.getAll(connex.getConnection());
            for (Object vente : ventes) {
                System.out.println(((Vente) vente).getQuantite());
                System.out.println(((Vente) vente).getDate_vente());
            }
            Vente new_vente = new Vente();
            System.out.println(new_vente.getId_vente());
            new_vente.setId_produit(1);
            new_vente.setId_point_vente(1);
            new_vente.setId_chariot(1);
            new_vente.setQuantite(12);
            new_vente.setDate_vente(java.sql.Date.valueOf("2024-02-02"));
            new_vente.save(connex.getConnection());

        }catch (Exception e){
            System.out.println(e.getCause());
        }

    }

}