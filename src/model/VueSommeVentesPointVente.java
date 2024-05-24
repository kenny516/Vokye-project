package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VueSommeVentesPointVente {
    private int idPointVente;
    private String localisation;
    private double totalVentes;

    // Getters et Setters
    public int getIdPointVente() {
        return idPointVente;
    }

    void setIdPointVente(int idPointVente) {
        this.idPointVente = idPointVente;
    }

    public String getLocalisation() {
        return localisation;
    }

    void setLocalisation(String localisation) {
        this.localisation = localisation;
    }


    public double getTotalVentes() {
        return totalVentes;
    }

    void setTotalVentes(double totalVentes) {
        this.totalVentes = totalVentes;
    }

    // Constructeurs
    public VueSommeVentesPointVente() {
    }

    public VueSommeVentesPointVente(int idPointVente, String localisation, String dateVente, double totalVentes) {
        this.idPointVente = idPointVente;
        this.localisation = localisation;
        this.totalVentes = totalVentes;
    }

    // Méthode pour récupérer les données de la vue sans filtrer par date 
    // raha tsy nandefa date
    public List<VueSommeVentesPointVente> getToutesVentes(Connection connection) throws SQLException {
        List<VueSommeVentesPointVente> list = new ArrayList<>();
        String query = "SELECT id_point_vente, localisation, SUM(total_ventes) AS tv \r\n" + //
                        "FROM vue_somme_ventes_point_vente \r\n" + //
                        "GROUP BY id_point_vente, localisation \r\n" + //
                        "ORDER BY tv DESC;\r\n" + //
                        "";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                VueSommeVentesPointVente vue = new VueSommeVentesPointVente();
                vue.setIdPointVente(resultSet.getInt("id_point_vente"));
                vue.setLocalisation(resultSet.getString("localisation"));
                vue.setTotalVentes(resultSet.getDouble("tv"));
                list.add(vue);
            }
        }
        return list;
    }

    // Méthode pour récupérer les données de la vue avec deux dates (dateMin et dateMax)
    // raha nandefa date anaky 2
    public List<VueSommeVentesPointVente> getVentesEntreDates(Connection connection, String dateMin, String dateMax) throws SQLException {
        List<VueSommeVentesPointVente> list = new ArrayList<>();
        String query = "SELECT id_point_vente, localisation, SUM(total_ventes) AS tv " +
                       "FROM vue_somme_ventes_point_vente " +
                       "WHERE date_vente BETWEEN '" + dateMin + "' AND '" + dateMax + "' " +
                       "GROUP BY id_point_vente, localisation " +
                       "ORDER BY tv DESC;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                VueSommeVentesPointVente vue = new VueSommeVentesPointVente();
                vue.setIdPointVente(resultSet.getInt("id_point_vente"));
                vue.setLocalisation(resultSet.getString("localisation"));
                vue.setTotalVentes(resultSet.getDouble("tv"));
                list.add(vue);
            }
        }
        return list;
    }
    
    

    // // Méthode pour récupérer les données de la vue à partir d'une date minimale
    public List<VueSommeVentesPointVente> getVentesAPartirDe(Connection connection, String dateMin) throws SQLException {
        List<VueSommeVentesPointVente> list = new ArrayList<>();
        String query = "SELECT id_point_vente, localisation, SUM(total_ventes) AS tv \r\n" + //
                        "FROM vue_somme_ventes_point_vente \r\n" + //
                        "WHERE date_vente >= '" + dateMin + "' " +
                       "GROUP BY id_point_vente, localisation " +
                       "ORDER BY tv DESC;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                VueSommeVentesPointVente vue = new VueSommeVentesPointVente();
                vue.setIdPointVente(resultSet.getInt("id_point_vente"));
                vue.setLocalisation(resultSet.getString("localisation"));
                vue.setTotalVentes(resultSet.getDouble("tv"));
                list.add(vue);
            }
        }
        return list;
    }

    // // Méthode pour récupérer les données de la vue jusqu'à une date maximale
    public List<VueSommeVentesPointVente> getVentesJusquA(Connection connection, String dateMax) throws SQLException {
        List<VueSommeVentesPointVente> list = new ArrayList<>();
        String query = "SELECT id_point_vente, localisation, SUM(total_ventes) AS tv \r\n" + //
                        "FROM vue_somme_ventes_point_vente \r\n" + //
                        "WHERE date_vente <= '" + dateMax + "' " +
                       "GROUP BY id_point_vente, localisation " +
                       "ORDER BY tv DESC;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                VueSommeVentesPointVente vue = new VueSommeVentesPointVente();
                vue.setIdPointVente(resultSet.getInt("id_point_vente"));
                vue.setLocalisation(resultSet.getString("localisation"));
                vue.setTotalVentes(resultSet.getDouble("tv"));
                list.add(vue);
            }
        }
        return list;
    }
}
