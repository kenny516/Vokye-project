package Controller;


import Model.Employe;
import bdd.Connexion;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;


public class ServletEmploye extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // create update
        Employe new_emp = new Employe();
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String numTel = req.getParameter("numTel");
        String dateNaissance = req.getParameter("dateNaissance");
        String dateEmbauche = req.getParameter("dateEmbauche");
        String poste = req.getParameter("poste");

        // Vérification des valeurs valides
        if (!isValidString(nom) || !isValidString(prenom) || !isValidString(numTel) || !isValidString(dateNaissance) || !isValidString(dateEmbauche) || !isValidString(poste)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Retourner un code d'erreur 400 si une valeur est manquante
            return;
        }

        // Si toutes les valeurs sont valides, les setter dans l'objet Employe
        //new_emp.setNom(nom);
        new_emp.setPrenom(prenom);
        new_emp.setNumTel(numTel);
        new_emp.setDateDeNaissance(dateNaissance);
        new_emp.setdateEntrer(dateEmbauche);
        new_emp.setPoste(poste);

        Connexion conn = new Connexion();
        try {
            new_emp.insertEmploye(conn.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // read delete
        String type = req.getParameter("type_dm");
        if (type.equals("liste")) {

            String poste = req.getParameter("poste");

            // Vérification de la valeur valide
            if (!isValidString(poste)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Retourner un code d'erreur 400 si le paramètre "poste" est manquant
                return;
            }

            Employe emp = new Employe();
            emp.setPoste(poste);

            Connexion conn = new Connexion();
            try {
                List<Employe> emp_list = emp.getListeEmployes(conn.getConnection());
                Gson json_change = new Gson();

                // Convertir la liste d'employés en format JSON
                String jsonListeEmployes = json_change.toJson(emp_list);

                // Définir le type de contenu de la réponse comme application/json
                resp.setContentType("application/json");

                // Écrire la liste JSON dans le flux de sortie de la réponse
                resp.getWriter().write(jsonListeEmployes);

                // Flusher et fermer le flux de sortie
                resp.getWriter().flush();
                resp.getWriter().close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if (type.equals("infos")){

            int id = Integer.parseInt(req.getParameter("id"));
            Connexion conn = new Connexion();

            try {
                Employe emp = Employe.getInfoEmployeById(conn.getConnection(),id);
                Gson json_change = new Gson();

                // Convertir la liste d'employés en format JSON
                String jsonListeEmploye = json_change.toJson(emp);

                // Définir le type de contenu de la réponse comme application/json
                resp.setContentType("application/json");

                // Écrire la liste JSON dans le flux de sortie de la réponse
                resp.getWriter().write(jsonListeEmploye);

                // Flusher et fermer le flux de sortie
                resp.getWriter().flush();
                resp.getWriter().close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }


}
