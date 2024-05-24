package pointVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PointVente {
    private int idPointVente;
    private String localisation;

    // Getters et Setters
    public int getIdPointVente() {
        return idPointVente;
    }

    void setIdPointVente(int idPointVente) {
        this.idPointVente = idPointVente;
    }

    public void setIdPointVente(String idPointVente) {
        setIdPointVente(Integer.parseInt(idPointVente));
    }

    public String getLocalisation() {
        return localisation;
    }

    void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public PointVente() {
    }

    public PointVente(int idPointVente) {
        this.idPointVente = idPointVente;
    }

    public PointVente(String localisation) {
        this.localisation = localisation;
    }

    public PointVente(int idPointVente, String localisation) {
        setIdPointVente(idPointVente);
        setLocalisation(localisation);
    }

    public List<PointVente> getListePointsVente(Connection connection) throws SQLException {
        List<PointVente> list = new ArrayList<>();
        String query = "SELECT * FROM point_vente";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                PointVente pointVente = new PointVente();
                pointVente.setIdPointVente(resultSet.getInt("id_point_vente"));
                pointVente.setLocalisation(resultSet.getString("localisation"));
                list.add(pointVente);
            }
        }
        return list;
    }

    public PointVente getInfoPointVenteById(Connection connection) throws SQLException {
        PointVente pointVente = null;
        String query = "SELECT * FROM point_vente WHERE id_point_vente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, getIdPointVente());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    pointVente = new PointVente();
                    pointVente.setIdPointVente(resultSet.getInt("id_point_vente"));
                    pointVente.setLocalisation(resultSet.getString("localisation"));
                }
            }
        }
        return pointVente;
    }

    public void insert(Connection connection) throws SQLException {
        String sql = "INSERT INTO point_vente (localisation) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, this.localisation);
            statement.executeUpdate();
        }
    }

    public void updatePointVente(Connection connection) throws SQLException {
        String sql = "UPDATE point_vente SET localisation = ? WHERE id_point_vente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, this.localisation);
            statement.setInt(2, this.idPointVente);
            statement.executeUpdate();
        }
    }

    public void deletePointVente(Connection connection, int idPointVente) throws SQLException {
        String sql = "DELETE FROM point_vente WHERE id_point_vente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPointVente);
            statement.executeUpdate();
        }
    }
}
