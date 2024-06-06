package com.mg.vokye.function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.model.Produit;


// responsable clientele
public class Function {
    public static String check_login(String nom, String prenom, String password) throws Exception {
        Connexion pgConnect = new Connexion();
        Connection connection = pgConnect.getConnection();
        String designation = null;
        
        String query = "SELECT designation FROM vue_employe_type WHERE nom = ? AND prenom = ? AND motDePasse = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, password);           
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // L'utilisateur existe, récupérer la désignation
                    designation = rs.getString("Designation");
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
        return designation;
    }
    

    public static int getNombreProduit() throws Exception {
        Connexion pgConnect = new Connexion();
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
    

    public static List<Produit> getListProduit() throws Exception {
        int k = Function.getNombreProduit();
        List<Produit> listProduit = new ArrayList<>();
        Connexion pgConnect = new Connexion();
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
            
            listProduit.add(produit); // Ajout de l'objet Produit à la liste
            index++;
        }
        
        // Fermer les ressources
        resultSet.close();
        preparedStatement.close();
        connection.close();
        
        return listProduit;
    }
    
    
}