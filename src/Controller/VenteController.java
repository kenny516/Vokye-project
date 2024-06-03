package Controller;


import bdd.Connexion;
import com.google.gson.Gson;
import model.Vente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@Controller
public class VenteController {
    Connexion connexion = new Connexion();


    @GetMapping("insert_vente")
    public String save(@RequestParam("id_chariot") int id_chariot,
                       @RequestParam("id_produit") int id_produit,
                       @RequestParam("quantite") int quantite,
                       @RequestParam("date_vente") LocalDate date_vente) {

        Vente vente = new Vente();
        vente.setId_chariot(id_chariot);
        vente.setId_produit(id_produit);
        vente.setQuantite(quantite);
        vente.setDate_vente(date_vente);
        try{
            vente.save(connexion.getConnection());
            return "succes";
        }catch (Exception ignored){
            return "failed";
        }
    }
    @GetMapping("getVentes")
    public String getVentes() throws Exception {
        Vente vente = new Vente();
        List<Object> ventes = vente.getAll(connexion.getConnection());
        Gson parseJson = new Gson();
        return parseJson.toJson(ventes);
    }

}
