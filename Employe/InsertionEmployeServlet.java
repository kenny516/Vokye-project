package servlet;

import connection.ConnectionPostgres;
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employe;

@WebServlet(name = "InsertionEmployeServlet", urlPatterns = { "/insertion-employe" })
public class InsertionEmployeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = ConnectionPostgres.getConnectionPostgres()) {
            List<Employe> employes = new Employe().getListeEmployes(connection, null);
            request.setAttribute("employes", employes);
            request.getRequestDispatcher("/pages/insertion/insertion-employe.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InsertionEmployeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String dateDeNaissance = request.getParameter("dateDeNaissance");
        String numTel = request.getParameter("numTel");
        String dateEntrer = request.getParameter("dateEntrer");
        String poste = request.getParameter("poste");
        Boolean statut = Boolean.valueOf(request.getParameter("statut"));

        try (Connection connection = ConnectionPostgres.getConnectionPostgres()) {
            Employe employe = new Employe();
            employe.setnom(nom);
            employe.setPrenom(prenom);
            employe.setDateDeNaissance(Timestamp.valueOf(dateDeNaissance));
            employe.setNumTel(Integer.parseInt(numTel));
            employe.setdateEntrer(Timestamp.valueOf(dateEntrer));
            employe.setPoste(poste);
            employe.setStatut(statut);

            employe.insertEmploye(connection);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InsertionEmployeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("insertion-employe");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for inserting an employee";
    }
}
