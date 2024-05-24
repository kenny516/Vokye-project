package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bdd.GenBDD;
import bdd.annotation.Colonne;

public class Employe extends GenBDD {
    @Colonne(nom = "id_employee")
    private int idEmployee;

    @Colonne(nom = "id_type_emp")
    private int idTypeEmployee;

    @Colonne(nom = "nom")
    private String nom;

    @Colonne(nom = "prenom")
    private String prenom;

    @Colonne(nom = "date_de_naissance")
    private Timestamp dateDeNaissance;

    @Colonne(nom = "num_tel")
    private int numTel;

    @Colonne(nom = "date_entrer")
    private Timestamp dateEntrer;

    @Colonne(nom = "date_fin")
    private Timestamp dateFin;

    @Colonne(nom = "poste")
    private String poste;

    @Colonne(nom = "statut")
    private Boolean statut;

    @Colonne(nom = "nbre_vente")
    private int numVente;

    @Colonne(nom = "chiffre_affaire")
    private Double chiffreAffaires;


    public int getidEmployee() {
        return idEmployee;
    }

    public void setidEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public void setidEmployee(String idEmployee) {
        setidEmployee(Integer.parseInt(idEmployee));
    }

    public int getIdTypeEmployee() {
        return idTypeEmployee;
    }

    public void setIdTypeEmployee(int idTypeEmployee) {
        this.idTypeEmployee = idTypeEmployee;
    }

    public void setIdTypeEmployee(String idTypeEmployee) {
        setIdTypeEmployee(Integer.parseInt(idTypeEmployee));
    }

    public int getNumVente() {
        return numVente;
    }

    public void setNumVente(int numVente) {
        this.numVente = numVente;
    }

    public void setNumVente(String numVente) {
        setNumVente(Integer.parseInt(numVente));
    }

    public String getnom() {
        return nom;
    }

    public void setnom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Timestamp getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Timestamp dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        setDateDeNaissance(Timestamp.valueOf(dateDeNaissance));
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public void setNumTel(String numTel) {
        setNumTel(Integer.parseInt(numTel));
    }

    public Double getChiffreAffaires() {
        return chiffreAffaires;
    }

    public void setChiffreAffaires(Double chiffreAffaires) {
        this.chiffreAffaires = chiffreAffaires;
    }

    public void setChiffreAffaires(String chiffreAffaires) {
        setChiffreAffaires(Double.parseDouble(chiffreAffaires));
    }

    public Timestamp getdateEntrer() {
        return dateEntrer;
    }

    public void setdateEntrer(Timestamp dateEntrer) {
        this.dateEntrer = dateEntrer;
    }

    public void setdateEntrer(String dateEntrer) {
        setdateEntrer(Timestamp.valueOf(dateEntrer));
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public void setDateFin(String dateFin) {
        setDateFin(Timestamp.valueOf(dateFin));
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Boolean getStatut() {
        return statut;
    }

    void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public void setStatut(String statut) {
        setStatut(Boolean.parseBoolean(statut));
    }

    public Employe() {
    }

    public Employe(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Employe(String nom) {
        this.nom = nom;
    }

    public Employe(int idEmployee, int idTypeEmployee, String nom, String prenom, int numTel, int numVente, String poste) {
        setidEmployee(idEmployee);
        setIdTypeEmployee(idTypeEmployee);
        setnom(nom);
        setPrenom(prenom);
        setNumTel(numTel);
        setNumVente(numVente);
        setPoste(poste);
    }

    public List<Employe> getListeEmployes(Connection connection) throws SQLException {
        List<Employe> list = new ArrayList<>();
        String query = "SELECT * FROM employe";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Employe employe = new Employe();
                employe.setidEmployee(resultSet.getInt("id_employee"));
                employe.setIdTypeEmployee(resultSet.getInt("id_type_emp"));
                employe.setnom(resultSet.getString("nom"));
                employe.setPrenom(resultSet.getString("prenom"));
                employe.setPoste(resultSet.getString("poste"));
                employe.setStatut(resultSet.getString("statut"));
                list.add(employe);
            }
        }
        return list;
    }


    public static Employe getInfoEmployeById(Connection connection, int idEmployee) throws SQLException {
        Employe employe = null;
        String query = "SELECT * FROM employe WHERE id_employee = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEmployee);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    employe = new Employe();
                    employe.setidEmployee(resultSet.getInt("id_employee"));
                    employe.setIdTypeEmployee(resultSet.getInt("id_type_emp"));
                    employe.setnom(resultSet.getString("nom"));
                    employe.setPrenom(resultSet.getString("prenom"));
                    employe.setNumVente(resultSet.getInt("nbre_vente"));
                    employe.setChiffreAffaires(resultSet.getDouble("chiffre_affaire"));
                }
            }
        }
        return employe;
    }

    public Employe getPosteEmploye(Connection connection) throws SQLException {
        Employe employe = null;
        String query = "SELECT * FROM employe WHERE poste = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, this.poste);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    employe = new Employe();
                    employe.setidEmployee(resultSet.getInt("id_employee"));
                    employe.setIdTypeEmployee(resultSet.getInt("id_type_emp"));
                    employe.setnom(resultSet.getString("nom"));
                    employe.setPrenom(resultSet.getString("prenom"));
                    employe.setPoste(resultSet.getString("poste"));
                }
            }
        }
        return employe;
    }

    public void insertEmploye(Connection connection) throws SQLException {
        try {
            this.save(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmploye(Connection connection) throws SQLException {
        try {
            this.update(connection, this.idEmployee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmploye(Connection connection) throws SQLException {
        try {
            this.supp(connection, this.idEmployee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
