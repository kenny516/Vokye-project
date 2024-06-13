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
    public static boolean check_login(String email, String password) throws Exception {
        Connexion pgConnect = new Connexion();
        Connection connection = pgConnect.getConnection();
        // designation soit responsable cliente ou admin
        
        // La requête SQL utilise des placeholders pour les paramètres email et motDePasse
        String query = "SELECT * FROM vue_employe_type WHERE (designation = 'responsable cliente' OR designation = 'admin') AND email = ? AND motDePasse = sha1(?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // L'utilisateur existe, récupérer la désignation
                    return true;
                }
            }
        } catch (SQLException e) {
            // Gérez les exceptions SQL ici
            e.printStackTrace();
            return false;
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static List<Produit> getListProduit() throws Exception {
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