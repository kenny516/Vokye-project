package com.mg.vokye.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.model.*;

@WebServlet(name = "UpdateDeletePointVenteServlet" , urlPatterns = {"/updateDeletePointVenteServlet"})
public class UpdateDeletePointVenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connexion connexion = new Connexion();
        Connection connection = null;

        try {
            connection = connexion.getConnection();
            int idPointVente = Integer.parseInt(request.getParameter("idPointVente"));
            String localisation = request.getParameter("localisation");
            PointVente pointVente = new PointVente(idPointVente, localisation);
            pointVente.update(connection,pointVente.getIdPointVente());
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
        String action = request.getParameter("action");
        Connexion connexion = new Connexion();
        Connection connection = null;

        try {
            connection = connexion.getConnection();
            if ("update".equals(action)) {
                int idPointVente = Integer.parseInt(request.getParameter("idPointVente"));
                String localisation = request.getParameter("localisation");
                request.setAttribute("idPointVente", idPointVente);
                request.setAttribute("localisation", localisation);
                request.getRequestDispatcher("updatePointVente.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int idPointVente = Integer.parseInt(request.getParameter("idPointVente"));
                PointVente pointVente = new PointVente();
                pointVente.supp(connection,idPointVente);
                response.sendRedirect("ListPointVenteServlet");
            }
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
}
