package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Unite {
    int id_unite;
    String nom_unite;

    public Unite() {}
    
    public Unite(int id_unite, String nom_unite) {
        this.id_unite = id_unite;
        this.nom_unite = nom_unite;
    }
    public int getId_unite() {
        return id_unite;
    }
    public void setId_unite(int id_unite) {
        this.id_unite = id_unite;
    }
    public String getNom_unite() {
        return nom_unite;
    }
    public void setNom_unite(String nom_unite) {
        this.nom_unite = nom_unite;
    }

    // methods
    // Méthode pour récupérer une unité par son ID
    public Unite get_unite_by_id(Connection connection) throws Exception {
        Unite unite = null;

        String sql = "SELECT * FROM Unite WHERE id_unite = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, this.id_unite);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            unite = new Unite();
            unite.setId_unite(resultSet.getInt("id_unite"));
            unite.setNom_unite(resultSet.getString("nom_unite"));
        }

        // Fermer les ressources
        resultSet.close();
        preparedStatement.close();

        return unite;
    }
}
