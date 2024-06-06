package com.mg.vokye.Controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;



import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.model.PointVente;

@WebServlet(name = "ListPointVenteServlet" , urlPatterns = {"/listPointVenteServlet"})
public class ListPointVenteServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connexion connexion = new Connexion();
        Connection connection = null;

        try {
            connection = connexion.getConnection();
            PointVente pointVente = new PointVente();
            List<PointVente> list = pointVente.getAll(connection);
            request.setAttribute("listPointVente", list);
            request.getRequestDispatcher("listPointsVente.jsp").forward(request, response);
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
