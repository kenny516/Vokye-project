package com.mg.vokye.function;

import java.sql.*;

public class GestionVentes {
    private final Connection conn;

    public GestionVentes(Connection conn) {
        this.conn = conn;
    }

    public double somme_vent_vendeur(int id_employee) {
        double somme = 0;
        try {
            String sql = "SELECT SUM(prix * quantite) FROM vente JOIN Produit ON vente.id_produit = Produit.id_produit WHERE id_employee = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_employee);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                somme = rs.getDouble(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return somme;
    }

    public double somme_vente_by_date(Date date_vente) {
        double somme = 0;
        try {
            String sql = "SELECT SUM(prix * quantite) FROM vente JOIN Produit ON vente.id_produit = Produit.id_produit WHERE id_employee = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_employee);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                somme = rs.getDouble(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return somme;
    }

    public double somme_perte_vendeur(int id_employee) {
        double somme = 0;
        try {
            String sql = "SELECT SUM(prix) FROM depense WHERE id_employee = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_employee);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                somme = rs.getDouble(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return somme;
    }

    public void get_vente_vendeur(int id_employee) {
        try {
            String sql = "SELECT Produit.nom, vente.quantite, vente.date_vente FROM vente JOIN Produit ON vente.id_produit = Produit.id_produit WHERE id_employee = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_employee);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Ventes du vendeur : ");
            while (rs.next()) {
                String nom_produit = rs.getString("nom");
                int quantite = rs.getInt("quantite");
                Date date_vente = rs.getDate("date_vente");
                System.out.println(nom_produit + " - Quantité: " + quantite + " - Date de vente: " + date_vente);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void get_perte_vendeur(int id_employee) {
        try {
            String sql = "SELECT Designation, prix, date_depense FROM depense JOIN type_depense ON depense.id_type_depense = type_depense.id_type_depense WHERE id_employee = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_employee);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Dépenses du vendeur : ");
            while (rs.next()) {
                String designation = rs.getString("Designation");
                double prix = rs.getDouble("prix");
                Date date_depense = rs.getDate("date_depense");
                System.out.println(designation + " - Prix: " + prix + " - Date de dépense: " + date_depense);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/voke", "username", "password");
            GestionVentes gestionVentes = new GestionVentes(conn);
            int id_employee = 1; // ID du vendeur
            System.out.println("Somme des ventes du vendeur : " + gestionVentes.somme_vent_vendeur(id_employee));
            System.out.println("Somme des dépenses du vendeur : " + gestionVentes.somme_perte_vendeur(id_employee));
            gestionVentes.get_vente_vendeur(id_employee);
            gestionVentes.get_perte_vendeur(id_employee);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
