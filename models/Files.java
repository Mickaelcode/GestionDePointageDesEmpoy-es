package models;

import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import controllers.NumberToWords;

public class Files {
    private String n_numEmp;
    private Timestamp n_date;
    private String nom,prenom,poste;
    private int absence,salary,year,mounth,days;

    public Files(String numEmp,Timestamp date){
       n_numEmp = numEmp;
       n_date = date;
       
    }

    public String getName(){
        return nom;
    }

    public String getFirstName(){
        return prenom;
    }

    public String getPoste(){
        return poste;
    }

    public int getAbsence(){
        return absence;
    }

    public int getSalary(){
        return salary;
    }

    public int getYears(){
        return year;
    }

    public int getMonths(){
        return mounth;
    }

    public int getDays(){
        return days;
    }

    private Paragraph writePg(String txt, int al) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(txt);
        paragraph.setAlignment(al);
        paragraph.setLeading(30);
        return paragraph;
    }

    public void bodyFile() throws SQLException{
        EmployModel employe = new EmployModel(n_numEmp);
        PointageModel pointage = new PointageModel(n_numEmp,0);
        absence = pointage.compteAbsent();
        ResultSet res = employe.rechFile();
        if (res!=null) { 
            while (res.next()) {
                nom = res.getString("nom");
                prenom = res.getString("prenom");
                poste = res.getString("poste");
                salary = res.getInt("salaire");
            }
        }

        year = n_date.getYear()+1900;
        mounth = n_date.getMonth()+1;
        days = n_date.getDate();
    }
    public void createFile() {
        
        try {
            bodyFile();
            Document doc = new Document();
            doc.setPageSize(PageSize.A5.rotate());
            PdfWriter.getInstance(doc, new FileOutputStream("test.pdf"));
            doc.open();
            doc.add(writePg("****************FICHE DE PAYEMENT****************", 1));
            if ( days < 10 && mounth < 10)doc.add(writePg("DATE : 0" +days +"/0" + mounth +"/" + year , 0));
            else if (days < 10 && mounth > 10)doc.add(writePg("DATE : 0" +days +"/" + mounth +"/" + year , 0));
            else if (days > 10 && mounth < 10)doc.add(writePg("DATE : " +days +"/0" + mounth +"/" + year , 0));
            else doc.add(writePg("DATE : " +days +"/" + mounth +"/" + year , 0));

            doc.add(writePg("NOM : " + nom, 0));
            doc.add(writePg("PRENOMS : " +prenom, 0));
            doc.add(writePg("POSTE : "+ poste , 0));
            doc.add(writePg( "NOMBRE D'ABSENCE : "+absence , 0));
            doc.add(writePg("MONTANT : " + salary +" Ar" + " ("+ NumberToWords.convert(salary)+" Ariary)", 0));
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
