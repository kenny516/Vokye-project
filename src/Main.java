import bdd.Connexion;
import model.Vente;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Vente vt = new Vente();
        Connexion connex = new Connexion();
        List<Object> ventes =  vt.getAll(connex.getConnection());
        for (Object vente : ventes) {
            System.out.println(((Vente) vente).getQuantite());
        }
    }

}