package servlet;

import connection.ConnectionPostgres;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employe;

@WebServlet(name = "InsertionEmployeServlet", urlPatterns = { "/insertion-employe" })
public class InsertionEmployeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection connection = ConnectionPostgres.getConnectionPostgres()) {
            List<Employe> employes = new Employe().getListeEmployes(connection, null);
            Gson gson = new Gson();
            String employesJson = gson.toJson(employes);

            try (PrintWriter out = response.getWriter()) {
                out.print(employesJson);
                out.flush();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InsertionEmployeServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"" + ex.getMessage() + "\"}");
                out.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

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

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"status\":\"success\"}");
                out.flush();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InsertionEmployeServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"" + ex.getMessage() + "\"}");
                out.flush();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for inserting an employee";
    }
}
