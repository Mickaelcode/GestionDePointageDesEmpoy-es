package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    public Connection conn = null;
    public Connexion() throws SQLException{
        String url ="jdbc:postgresql://localhost:5432/javapro";
        String user = "postgres";
        String password = "Mika";
        try{
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connecting to database ");
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connetion: " + e.getMessage());
        }
    }

    public void closeConnexion() throws SQLException{
        conn.close();
    }
}
