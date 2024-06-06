package com.mg.vokye.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.model.PointVente;

@WebServlet(name = "InsertPointVenteServlet" , urlPatterns = {"/insertPointVenteServlet"})
public class InsertPointVenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String localisation = request.getParameter("localisation");
        Connexion connexion = new Connexion();
        Connection connection = null;

        try {
            connection = connexion.getConnection();
            PointVente pointVente = new PointVente();
            pointVente.setLocalisation(localisation);
            pointVente.save(connection);
            response.sendRedirect("ListPointVenteServlet");
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
