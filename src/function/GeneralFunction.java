package function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneralFunction {
    
    // Méthode pour récupérer les noms des produits
    public static List<String> getNomProduit(Connection conn) throws SQLException {
        String query = "SELECT nom FROM Produit";
        List<String> nomsProduits = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nomsProduits.add(rs.getString("nom"));
            }
        }

        return nomsProduits;
    }

    // Méthode pour calculer le bénéfice total par produit
    public static Map<String, Double> Total_benefice_par_produit(Connection conn) throws SQLException {
        String query = "SELECT p.nom, SUM(v.quantite * p.prix) AS total_benefice " +
                       "FROM Produit p JOIN vente v ON p.id_produit = v.id_produit " +
                       "GROUP BY p.nom";
        Map<String, Double> beneficeParProduit = new HashMap<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                beneficeParProduit.put(rs.getString("nom"), rs.getDouble("total_benefice"));
            }
        }

        return beneficeParProduit;
    }

    // Méthode pour calculer la perte totale par produit
    public static Map<String, Double> Total_perte_par_produit(Connection conn) throws SQLException {
        String query = "SELECT p.nom, SUM(a.prix) AS total_perte " +
                       "FROM Produit p JOIN achat_ingredient a ON p.id_produit = a.id_ingredient " +
                       "GROUP BY p.nom";
        Map<String, Double> perteParProduit = new HashMap<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                perteParProduit.put(rs.getString("nom"), rs.getDouble("total_perte"));
            }
        }

        return perteParProduit;
    }

    // Méthode pour classer les produits par bénéfice total
    public static List<Map.Entry<String, Double>> Classement_produits(Connection conn) throws SQLException {
        Map<String, Double> benefices = Total_benefice_par_produit(conn);

        return benefices.entrySet()
                        .stream()
                        .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                        .collect(Collectors.toList());
    }
}
