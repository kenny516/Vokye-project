package model;

import bdd.annotation.*;
import bdd.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(nom="Utilitaire")
public class Equipement extends GenBDD {
    @Colonne(nom="id_utilitaire")
    private int id;
    @Colonne(nom="nom")
    private String nom;
    private int quantite;
    @Colonne(nom="id_unite")
    private int idUnite;
    private String nomUnite;
    private String etat;
    private LocalDate date_etat;

    // Constructeur
    public Equipement() {
    }

    public Equipement(String nom) {
        this.nom = nom;
    }

    public Equipement(int id) {
        this.id = id;
    }

    public Equipement(int id, String nom, int idUnite) {
        this.id = id;
        this.nom = nom;
        this.idUnite = idUnite;
    }

    public Equipement(int id, String nom, int quantite, int idUnite, String nomUnite) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.idUnite = idUnite;
        this.nomUnite = nomUnite;
    }

    public Equipement(int id, String nom, int quantite, int idUnite, String nomUnite, String etat, LocalDate date_etat) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.idUnite = idUnite;
        this.nomUnite = nomUnite;
        this.etat = etat;
        this.date_etat = date_etat;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getIdUnite() {
        return idUnite;
    }

    public void setIdUnite(int idUnite) {
        this.idUnite = idUnite;
    }

    public String getNomUnite() {
        return nomUnite;
    }

    public void setNomUnite(String nomUnite) {
        this.nomUnite = nomUnite;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDate getDateEtat() {
        return date_etat;
    }

    public void setDateEtat(LocalDate date_etat) {
        this.date_etat = date_etat;
    }

    // Méthode pour récupérer tous les équipements
    public static List<Equipement> getAllEquipement(Connection conn) throws SQLException {
        String query = "SELECT * FROM v_equipement";
        List<Equipement> equipements = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Equipement equipement = new Equipement(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getInt("quantite"),
                    rs.getInt("id_unite"),
                    rs.getString("nom_unite")
                );
                equipements.add(equipement);
            }
        }

        return equipements;
    }

    // Méthode pour mettre à jour la quantité d'un équipement
    public static void updateQuantite(Connection conn, int id, int newQuantite) throws SQLException {
        String query = "UPDATE Stock_utilitaire SET quantite = ? WHERE id_utilitaire = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newQuantite);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // Méthode pour récupérer un équipement par ID
    public static Equipement getEquipementById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM v_equipement WHERE id = ?";
        Equipement equipement = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    equipement = new Equipement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("quantite"),
                        rs.getInt("id_unite"),
                        rs.getString("nom_unite")
                    );
                }
            }
        }

        return equipement;
    }

    public void get_info_etat(Connection conn) throws SQLException {
        String query = "SELECT et.nom as nom_etat, eu.date_etat_utilitaire as date_etat FROM Etat_utilitaire eu JOIN Etat et ON eu.id_etat = et.id_etat WHERE eu.id_utilitaire = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.setEtat(rs.getString("nom_etat"));
                    this.setDateEtat(rs.getDate("date_etat").toLocalDate());
                }
            }
        }
    }
}
