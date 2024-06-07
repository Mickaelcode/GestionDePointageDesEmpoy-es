package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;


import controllers.NumberToWords;
import models.Files;

public class Fiche extends JDialog{
    private PointageView view;
    private JSpinner date;
    private JLabel numEmp,datLabel;
    private JButton pdfBtn ;
    private Files file,body;
    private JComboBox employ;
    private JTextArea txt;
    private String nom,prenom,poste;
    private int absence,salary,year,mounth,days;
    public Fiche(){
        this.setLayout(null);
        this.setTitle("****FICHE DE PAYEMENT****");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(620,500);
        this.setLocation(800,200);
        showEmp();
        showDate();
        TextArea();
        employ.setEditable(false);
        txt.setBackground(Color.GRAY);
        txt.setEditable(false);
        Font police = new Font("Cursive", Font.ITALIC, 13);
        txt.setFont(police );
        txt.setForeground(Color.white);
        pdfBtn = new JButton("TELECHARGER");
        pdfBtn.setBackground(Color.MAGENTA);
        pdfBtn.setBounds(200, 400, 200, 30);
        pdfBtn.setForeground(Color.white);
        this.add(pdfBtn);
        printPdf(pdfBtn);
        printFileTxt(employ);
        this.setVisible(true);



    }
       public void updateCombox(){
        employ.removeAllItems();
        view = new PointageView(0);
        String [] data = view.dataCombox();
        for (String elem : data) {
            employ.addItem(elem);
            
        }
        txt.setText("");
        txt.setText("******************************FICHE DE PAYEMENT************************************");
        employ.setSelectedItem("");
        this.add(employ);
    }
    private void TextArea(){
        txt = new JTextArea();
        txt.setText("******************************FICHE DE PAYEMENT************************************");
        txt.setBounds(30, 200, 550, 200);
        this.add(txt);
    }

    private void printFileTxt(JComboBox eBox){
        eBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e){
                showText();
            }
        });
    }

    private void clearTxt(){
        txt.setText("");
        txt.setText("******************************FICHE DE PAYEMENT************************************");
        employ.setSelectedItem("");
    }

    private void showText(){
        txt.setText("");
        try{
            java.util.Date utilDate = (java.util.Date) date.getValue();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Timestamp time = new Timestamp(sqlDate.getTime());
            String numEmp = (String) employ.getSelectedItem();
            body  = new Files(numEmp,time);
            body.bodyFile();

            nom = body.getName();
            prenom = body.getFirstName();
            poste = body.getPoste();
            absence = body.getAbsence();
            salary = body.getSalary();
            year = body.getYears();
            mounth = body.getMonths();
            days = body.getDays();
            String dateTxt = showDateTxt(year,mounth,days);
            if(employ.getSelectedItem()!=""){

                txt.setText("******************************FICHE DE PAYEMENT************************************\n"
                +"DATE : "+dateTxt+"\n"+"NOM : " +nom +"\n" + "PRENOMS : " +prenom+"\n"+"POSTE : "+poste+"\n"
                +"NOMBRE D'ABSENCE : "+absence+"\n"+"MONTANT : "+salary+" AR  ("+NumberToWords.convert(salary)+" Ariary)");
                
            }else txt.setText("******************************FICHE DE PAYEMENT************************************"); 
            }catch(Exception event){
                event.printStackTrace();
                
            }

    }

    private String showDateTxt(int year,int month,int days){
        String dateTxt ="";
        if ( days < 10 && mounth < 10){
            dateTxt="DATE : 0" +days +"/0" + mounth +"/" + year ;
            return dateTxt;
        }
        else if (days < 10 && mounth > 10){
            dateTxt="DATE : 0" +days +"/" + mounth +"/" + year ; 
            return dateTxt;
        }
        else if (days > 10 && mounth < 10){
            dateTxt="DATE : " +days +"/0" + mounth +"/" + year ;
            return dateTxt;
        }
        else {
            dateTxt="DATE : " +days +"/" + mounth +"/" + year ;
            return dateTxt;
        }
    }


    private void showEmp(){
        numEmp = new JLabel("Numéro Employé:");
        view = new PointageView(0);
        employ = new JComboBox(view.dataCombox());
        employ.setSelectedItem("");
        numEmp.setBounds(30,100,150,30);
        employ.setBounds(30, 130, 100, 30);
        this.add(numEmp);
        this.add(employ);
    }
    private void showDate(){
        datLabel = new JLabel("Date:");
        SpinnerDateModel dateModel = new SpinnerDateModel();
        date = new JSpinner(dateModel);

        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(date, "dd//MM//yyyy");
        date.setEditor(dateEditor);

        JPanel paneldate = new JPanel();
        paneldate.add(date);
        paneldate.setSize(200, 50);
        paneldate.setBounds(-20, 60, 200, 50);
        datLabel.setBounds(30,30,150,30);
        this.add(datLabel);
        this.add(paneldate);
    }
    private void printPdf(JButton btn){
        btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                java.util.Date utilDate = (java.util.Date) date.getValue();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                Timestamp time = new Timestamp(sqlDate.getTime());
                String numEmp = (String) employ.getSelectedItem();
                try{
                    file  = new Files(numEmp,time);
                    file.createFile();
                    JOptionPane.showMessageDialog(null, "SUCCEES !!");
                    clearTxt();
                }catch(Exception event){
                    event.printStackTrace();

                }
            }
        });
    }
}
