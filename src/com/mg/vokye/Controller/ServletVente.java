package com.mg.vokye.Controller;

import com.google.gson.Gson;
import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.model.Vente;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ServletVente extends HttpServlet {
    private final Connexion connexion = new Connexion();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try (Connection connection = connexion.getConnection()) {
            String jsonData = null;
            Vente vente = new Vente();

            if ("read".equalsIgnoreCase(action)) {
                List<Object> ventes = vente.getAll(connection);
                jsonData = new Gson().toJson(ventes);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (jsonData != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(jsonData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equalsIgnoreCase(action)) {
            handleCreate(req, resp);
        } else if ("update".equalsIgnoreCase(action)) {
            handleUpdate(req, resp);
        } else if ("delete".equalsIgnoreCase(action)) {
            handleDelete(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid action for POST method");
        }
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id_chariot = Integer.parseInt(req.getParameter("id_chariot"));
            int id_produit = Integer.parseInt(req.getParameter("id_produit"));
            int quantite = Integer.parseInt(req.getParameter("quantite"));

            String dateString = req.getParameter("date_vente");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date_vente;

            try {
                date_vente = formatter.parse(dateString);
            } catch (ParseException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            try (Connection connection = connexion.getConnection()) {
                Vente new_vente = new Vente();
                new_vente.setId_chariot(id_chariot);
                new_vente.setId_produit(id_produit);
                new_vente.setQuantite(quantite);
                new_vente.setDate_vente(date_vente);
                new_vente.save(connection);

                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Insert successful");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Insert failed");
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id_vente = Integer.parseInt(req.getParameter("id_vente"));
            int id_chariot = Integer.parseInt(req.getParameter("id_chariot"));
            int id_produit = Integer.parseInt(req.getParameter("id_produit"));
            int quantite = Integer.parseInt(req.getParameter("quantite"));

            String dateString = req.getParameter("date_vente");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date_vente;

            try {
                date_vente = formatter.parse(dateString);
            } catch (ParseException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            try (Connection connection = connexion.getConnection()) {
                Vente updated_vente = new Vente();
                updated_vente.setId_vente(id_vente);
                updated_vente.setId_chariot(id_chariot);
                updated_vente.setId_produit(id_produit);
                updated_vente.setQuantite(quantite);
                updated_vente.setDate_vente(date_vente);
                updated_vente.update(connection,updated_vente.getId_vente());

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Update successful");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Update failed");
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id_vente = Integer.parseInt(req.getParameter("id_vente"));

            try (Connection connection = connexion.getConnection()) {
                Vente vente = new Vente();
                vente.supp(connection,id_vente);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Delete successful");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Delete failed");
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
