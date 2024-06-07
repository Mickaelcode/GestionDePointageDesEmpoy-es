package models;

import java.sql.*;

public class EmployModel {
    private String n_numberEmploye, n_name, n_firstName, n_post;
    private int n_salary;
    public Connexion connex = null;
    public ResultSet res = null;

    public EmployModel() throws SQLException {
        // System.out.println("Starting");
        connex = new Connexion();
    }

    public EmployModel(String number, String name, String firstname, String post, int salary) throws SQLException {
        connex = new Connexion();
        n_numberEmploye = number;
        n_name = name;
        n_firstName = firstname;
        n_post = post;
        n_salary = salary;
    }

    public EmployModel(String number) throws SQLException {
        connex = new Connexion();
        n_numberEmploye = number;
    }

    public String getName() {
        return n_name;
    }

    public void addEmploy() throws SQLException {
        String query = "INSERT INTO employe values(?,?,?,?,?)";
        try {
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setString(1, n_numberEmploye);
            ps.setString(2, n_name);
            ps.setString(3, n_firstName);
            ps.setString(4, n_post);
            ps.setInt(5, n_salary);
            int nbr = ps.executeUpdate();
            System.out.println("Employé ajouté: " + nbr);
            connex.closeConnexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectEmploye() {
        String query = "SELECT * FROM employe order by numemp asc";
        try {
            Statement stm = connex.conn.createStatement();
            res = stm.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void updateEmploye() throws SQLException {
        String query = "UPDATE employe SET Nom=?, Prenom=?, Poste=?, Salaire=? WHERE Numemp=?";
        try {
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setString(1, n_name);
            ps.setString(2, n_firstName);
            ps.setString(3, n_post);
            ps.setInt(4, n_salary);
            ps.setString(5, n_numberEmploye);
            int nbr = ps.executeUpdate();
            System.out.println("Employé mis à jour: " + nbr);
            connex.closeConnexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String deleteEmploy() throws SQLException {
        String query = "DELETE FROM employe WHERE Numemp=?";
        try {
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setString(1, n_numberEmploye);
            int nbr = ps.executeUpdate();
            System.out.println("Employé supprimé: " + nbr);
            connex.closeConnexion();
            return "ok";
        } catch (SQLException e) {
            // e.printStackTrace();
            String m =e.toString();
            return m;
            
            
        }
    }

    public ResultSet rechFile() throws SQLException {
        String query = "SELECT * FROM employe where numemp = ? ";
        try{
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setString(1, n_numberEmploye);
            res = ps.executeQuery();
            return res;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet rechercheEmploy() throws SQLException {
        String query = " SELECT nom,prenom,poste,CONCAT(TO_CHAR(salaire, 'L99G999D99') as salaire FROM employe WHERE nom LIKE ? OR prenom LIKE ?";
        try{
            PreparedStatement ps = connex.conn.prepareStatement(query);
            String search = "%" + n_numberEmploye +"%";
            ps.setString(1, search);
            ps.setString(2, search);
            res = ps.executeQuery();
            return res;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }
}
