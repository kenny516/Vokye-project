package com.mg.vokye.Controller;

import com.google.gson.Gson;
import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.model.Vente;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/voke")
public class VenteController {
    Connexion connexion = new Connexion();

    @GetMapping("/insert_vente")
    @ResponseBody
    public String save(@RequestParam("id_chariot") int id_chariot,
                       @RequestParam("id_produit") int id_produit,
                       @RequestParam("quantite") int quantite,
                       @RequestParam("date_vente") LocalDate date_vente) {

        Vente vente = new Vente();
        vente.setId_chariot(id_chariot);
        vente.setId_produit(id_produit);
        vente.setQuantite(quantite);
        vente.setDate_vente(date_vente);
        try {
            vente.save(connexion.getConnection());
            return "success";
        } catch (Exception ignored) {
            return "failed";
        }
    }

    @GetMapping("/getVentes")
    @ResponseBody
    public String getVentes() throws Exception {
        Vente vente = new Vente();
        List<Object> ventes = vente.getAll(connexion.getConnection());
        Gson parseJson = new Gson();
        return parseJson.toJson(((Vente)ventes.get(0)).getQuantite());
    }
}
