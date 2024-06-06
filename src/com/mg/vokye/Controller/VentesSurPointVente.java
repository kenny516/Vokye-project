package com.mg.vokye.Controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.model.VueSommeVentesPointVente;

@WebServlet(name = "VentesSurPointVente" , urlPatterns = {"/ventesSurPointVente"})
public class VentesSurPointVente extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateMin = request.getParameter("dateMin");
        String dateMax = request.getParameter("dateMax");
        Connexion connexion = new Connexion();
        Connection connection = null;

        try {
            connection = connexion.getConnection();
            VueSommeVentesPointVente vue = new VueSommeVentesPointVente();
            List<VueSommeVentesPointVente> list;

            if (dateMin != null && !dateMin.isEmpty() && dateMax != null && !dateMax.isEmpty()) {
                list = vue.getVentesEntreDates(connection, dateMin, dateMax);
            } else if (dateMin != null && !dateMin.isEmpty()) {
                list = vue.getVentesAPartirDe(connection, dateMin);
            } else if (dateMax != null && !dateMax.isEmpty()) {
                list = vue.getVentesJusquA(connection, dateMax);
            } else {
                list = vue.getToutesVentes(connection);
            }

            request.setAttribute("listVentes", list);
            request.getRequestDispatcher("ventesResult.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
