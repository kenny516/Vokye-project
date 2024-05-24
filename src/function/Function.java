/*
    
*/

package function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Produit;
import bdd.Connect;


public class Function {
    public static boolean check_login(String email, String password) throws Exception {
        Connect pgConnect = new Connect();
        Connection connection = pgConnect.getConnection();
        boolean isValidUser = false;
        
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // L'utilisateur existe
                    isValidUser = true;
                    return isValidUser;
                }
            }
        } catch (SQLException e) {
            // Gérez les exceptions SQL ici
            e.printStackTrace();
            throw new Exception("Database query error", e);
        } finally {
            // Fermez la connexion à la base de données
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isValidUser;
    }

    public static int getNombreProduit() throws Exception {
        Connect pgConnect = new Connect();
        int count = 0;
        Connection connection = pgConnect.getConnection();
    
        String sql = "select count(*) from produit";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
    
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
    
        // Fermer les ressources
        resultSet.close();
        preparedStatement.close();
        connection.close();
    
        return count;
    }
    

    public static Produit[] getListProduit() throws Exception {
        int k = Function.getNombreProduit();
        Produit[] listProduit = new Produit[k];
        Connect pgConnect = new Connect();
        Connection connection = pgConnect.getConnection();
        
        String sql = "select * from produit";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        int index = 0;
        while(resultSet.next() && index < 5) {
            Produit produit = new Produit();
            // Supposons que votre classe Produit ait des méthodes setters pour définir ses propriétés
            produit.setIdProduit(resultSet.getInt("id_produit"));
            produit.setNomProduit(resultSet.getString("nom"));
            produit.setPrix(resultSet.getDouble("prix"));
            // et ainsi de suite pour d'autres propriétés
            
            listProduit[index] = produit;
            index++;
        }
        
        // Fermer les ressources
        resultSet.close();
        preparedStatement.close();
        connection.close();
        
        return listProduit;
    }
    
}