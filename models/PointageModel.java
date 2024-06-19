package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class PointageModel {
    public Connexion connex;
    private Timestamp n_datePointage;
    private String n_datesupp;
    private String n_numEmp,n_pointage;
    public ResultSet res;
    public PointageModel() throws SQLException{
        connex = new Connexion();
    }

    public PointageModel(String datePointage) throws SQLException{
        n_datesupp = datePointage;
        connex = new Connexion();
    }

    public PointageModel(Timestamp datePointage) throws SQLException{
        n_datePointage = datePointage;
        connex = new Connexion();
    }

    public PointageModel(Timestamp datePointage,String numEmp,String pointage) throws SQLException{
        n_datePointage = datePointage;
        n_numEmp = numEmp;
        n_pointage = pointage;
        connex = new Connexion();

    }

    public PointageModel(String datePointage,String numEmp,String pointage) throws SQLException{
        n_datesupp = datePointage;
        n_numEmp = numEmp;
        n_pointage = pointage;
        connex = new Connexion();

    }

    public PointageModel(String numEmp,int i) throws SQLException {
        n_numEmp = numEmp;
        connex = new Connexion();
    }


    //CRUD

    public int compteAbsent(){
        String query = "select count(numemp) as absence from pointage where numemp =?";
        int b = 0;
        try{
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setString(1, n_numEmp);
            res = ps.executeQuery();
            if (res!=null){
                while (res.next()) {
                    b = res.getInt("absence");
                }
            }
            return b;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }

    public  void addPointage(){
        String  query = "INSERT INTO POINTAGE VALUES(?,?,?)";
        String queryupdateMiss = "update employe set salaire = salaire - 10000 WHERE numemp =? ";
        try{
            PreparedStatement ps = connex.conn.prepareStatement(query);
            ps.setTimestamp(1,n_datePointage);
            ps.setString(2, n_numEmp);
            ps.setString(3, n_pointage);
            int nb = ps.executeUpdate();

            if( n_pointage=="Absent"){
                ps = connex.conn.prepareStatement(queryupdateMiss);
                ps.setString(1, n_numEmp);
                nb = ps.executeUpdate();
               System.out.println("add kay!");;

            }
           
            System.out.println("yess "+nb);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public ResultSet readPointage(){
        String query = "Select * from pointage order by datePointage asc";
         
        try{
            Statement stm = connex.conn.createStatement();
            res = stm.executeQuery(query);
            System.out.println(" yes "+ res);
            return res;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public void updatePointage(){
        String query = "UPDATE POINTAGE SET numemp=?,pointage=? where datepointage=\'"+n_datesupp+"\'";
        String queryupdateHere = "update employe set salaire = salaire + 10000 WHERE numemp =? ";
        String queryupdateMiss = "update employe set salaire = salaire - 10000 WHERE numemp =? ";

        try{
            PreparedStatement ps =connex.conn.prepareStatement(query);
            ps.setString(1,n_numEmp);
            ps.setString(2,n_pointage);
            int nb = ps.executeUpdate();
            System.out.println("ok = "+nb);
            if( n_pointage=="Présent"){
                PreparedStatement ms = connex.conn.prepareStatement(queryupdateHere);
               ms.setString(1, n_numEmp);
               nb = ms.executeUpdate();
               System.out.println("present kay!");;
           }
            else if (n_pointage=="Absent"){
                PreparedStatement ks =connex.conn.prepareStatement(queryupdateMiss);
                ks.setString(1, n_numEmp);
                nb = ks.executeUpdate();
               System.out.println("absent kay!");;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deletePointage(){
        String query = "DELETE from pointage where datepointage=\'"+n_datesupp +"\'";

        try{
            Statement stm = connex.conn.createStatement();
          stm.executeQuery(query);
          System.out.println("Mety");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet researchPointage(){
        String query = " select * from pointage where to_char(datepointage, 'DD') like '"+n_datesupp+"' or to_char(datepointage, 'MM') like '"+n_datesupp+"' or to_char(datepointage, 'YYYY') like '"+n_datesupp+"'  or to_char(datepointage, 'DD-MM') like '"+n_datesupp+"'";
        try{
            Statement stm = connex.conn.createStatement();
            res = stm.executeQuery(query);
            System.out.println("ok recherche"+res);
            return res;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet SearchDate(){
        String query = " select * from pointage where to_char(datepointage, 'YYY-MM-DD') like ?";
            try{Statement stm = connex.conn.createStatement();
            res = stm.executeQuery(query);
            System.out.println("ok recherche"+res);
            return res;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }


    
}
