package models;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Conge {
    private String n_numConge ,n_numEmp,n_motif;
    private int n_nbJours;
    private Timestamp n_dateDemande,n_dateRetour;
    public Connexion connex;
    public ResultSet res;

    public Conge() throws SQLException{
        connex = new Connexion();
    }

    public Conge(String numConge) throws SQLException{
        n_numConge = numConge;
        connex = new Connexion();
    }

    public Conge(String numEmp,int second ) throws SQLException{
        n_numEmp = numEmp;
        connex = new Connexion();
    }

    // public Conge(String numConge,String numEmp,String motif,int nbJours) throws SQLException{
    //     n_numConge = numConge;
    //     n_numEmp = numEmp;
    //     n_motif = motif;
    //     n_nbJours = nbJours;
      
    
    //     connex = new Connexion();
    // }
    public Conge(String numConge,String numEmp,String motif,int nbJours,Timestamp dateDemande,Timestamp dateRetour) throws SQLException{
        n_numConge = numConge;
        n_numEmp = numEmp;
        n_motif = motif;
        n_dateDemande = dateDemande;
        n_nbJours = nbJours;
        n_dateRetour = dateRetour;
        connex = new Connexion();
    }

    //CRUD

    public String addConge(){
        String response = "";
        int days = 0;
        String answer="";
        String queryDays = " select sum(nbrjr) as Jours from conge where numemp=?";
        String query ="insert into conge values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps =connex.conn.prepareStatement(queryDays);
            ps.setString(1, n_numEmp);
            res = ps.executeQuery();
            while (res.next()) {
              answer = res.getString("Jours");
              System.out.println("answer: " + answer);
            }
            if(answer==null){
                days=0;
                System.out.println("days null:"+days);
            }else {

                days=Integer.parseInt(answer);
            
                
                System.out.println("days 1: " + days);
            }

            days+=n_nbJours;
            System.out.println("days 2: " + days);
            if(days>30){
                System.out.println("Maximum");
                response = "Maximum";
                return response;
            }

            else{
                PreparedStatement ms = connex.conn.prepareStatement(query);
                ms.setString(1, n_numConge);
                ms.setString(2, n_numEmp);
                ms.setString(3, n_motif);
                ms.setInt(4, n_nbJours);
                ms.setTimestamp(5, n_dateDemande);
                ms.setTimestamp(6, n_dateRetour);

                int m =ms.executeUpdate();
                System.out.println("VITA : " +m);
                response = "VITA";
                return response;


            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return response;
    }
    public void updateConge(){
        String query = "update conge set numemp =?,motif =?,nbrjr =?,dateDemande=?,dateRetour=? where numconge = ?";
        try{
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setString(1, n_numEmp);
            ps.setString(2,n_motif);
            ps.setInt(3,n_nbJours);
            ps.setTimestamp(4,n_dateDemande);
            ps.setTimestamp(5,n_dateRetour);
            ps.setString(6,n_numConge);
            int nb = ps.executeUpdate();
            System.out.println("mety "+nb);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet readConge(){
        String query = "SELECT * FROM conge order by numconge asc";
        try{
            Statement stm = connex.conn.createStatement();
            res = stm.executeQuery(query);
            return res;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public void deleteConge(){
        String query = "DELETE FROM conge where numconge =?";
        try{
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setString(1, n_numConge);
            int nb = ps.executeUpdate();
            System.out.println("delete succes conge: " + nb);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int rechNbJr(){
        String query = " select sum(nbrjr) as nombreJoursTotal from conge where numemp=?";
        int sumDays = 0;
        int restDays = 0;
        String result ="";
        try {
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setString(1, n_numEmp);
            res = ps.executeQuery();
            while (res.next()) {
                
                result = res.getString("nombreJoursTotal");
                System.out.println(result);
            }
            if(result==null) sumDays=0;
            else sumDays = Integer.parseInt(result);
            
            System.out.println(sumDays);
            restDays = 30 - sumDays;
            return restDays;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restDays;
          
    }
}