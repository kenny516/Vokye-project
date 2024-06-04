package com.mg.vokye.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    Connection connex; 
    public Connection getConnection() throws Exception{
        try{
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/voke","postgres","2004");
        }
        catch(SQLException e){
            throw e;
        }
    }
}
