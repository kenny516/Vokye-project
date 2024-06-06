package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    Connection connex; 
    public Connection getConnection() throws Exception{
        try{
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nom_projet","nom_database","mdp");
            return connection;
        }
        catch(SQLException e){
            throw e;
        }
    }
}
