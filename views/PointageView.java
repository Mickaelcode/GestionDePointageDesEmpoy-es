package views;

import java.awt.Color;
import java.awt.Font;
// import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import models.EmployModel;
import models.PointageModel;

public class PointageView extends JPanel{
    private JTextField researchField;
    private  JButton addBtn, modifBtn, saveBtn, deleteBtn, rechButton;
    private JComboBox employ;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private JRadioButton here, mis;
    private JSpinner dateTime,dateTimeSearch;

    public PointageView(int m){
        
    }
    public PointageView(){
        JLabel title = new JLabel("POINTAGE DES EMPLOYES");
        JLabel numLabel = new JLabel("Numero Employe:");
        JLabel rechLabel = new JLabel("Rechercher");
        JLabel pointagLabel = new JLabel("Pointage");
        JLabel datLabel = new JLabel("Date:");
        this.setBackground(Color.darkGray);
        this.setLayout(null);
        
        researchField = new JTextField(15);
        researchField.setBounds(650, 60, 250, 30);
        rechLabel.setBounds(650, 30, 250, 30);
        this.add(researchField);
        this.add(rechLabel);
        
        addBtn = new JButton("AJOUTER");
        addBtn.setBounds(350, 130, 100, 30);
        modifBtn = new JButton("MODIFIER");
        modifBtn.setBounds(500, 130, 100, 30);
        saveBtn = new JButton("ENREGISTRER");
        saveBtn.setBounds(500, 130, 150, 30);
        saveBtn.setVisible(false);
        deleteBtn = new JButton("SUPPRIMER");
        deleteBtn.setBounds(650, 130, 150, 30);
        
        rechButton = new JButton();
        rechButton.setBackground(Color.BLUE);
        rechButton.setBounds(894, 60, 30, 30);
        
        this.add(addBtn);
        this.add(modifBtn);
        this.add(saveBtn);
        this.add(deleteBtn);
        this.add(rechButton);
        
        title.setBounds(390, 0, 300, 30);
        numLabel.setBounds(30, 30, 300, 30);
        pointagLabel.setBounds(200, 30, 300, 30);
        datLabel.setBounds(30, 100, 300, 30);
        Font police = new Font("Cursive", Font.BOLD, 19);
        title.setFont(police );
        title.setForeground(Color.white);
        numLabel.setForeground(Color.white);
        pointagLabel.setForeground(Color.white);
        datLabel.setForeground(Color.white);
        rechLabel.setForeground(Color.white);

        addBtn.setBackground(Color.BLUE);
        addBtn.setForeground(Color.white);
        modifBtn.setBackground(Color.GREEN);
        modifBtn.setForeground(Color.white);
        saveBtn.setBackground(Color.GREEN);
        saveBtn.setForeground(Color.white);
        deleteBtn.setBackground(Color.red);
        deleteBtn.setForeground(Color.white);
        

        
        this.add(pointagLabel);
        this.add(datLabel);
        
        this.add(title);
        this.add(numLabel);

        here = new JRadioButton("Oui");
        mis = new JRadioButton("Non");
        here.setBackground(Color.darkGray);
        mis.setBackground(Color.darkGray);
        here.setForeground(Color.white);
        mis.setForeground(Color.white);
        
        JPanel div = new JPanel();
        div.setBackground(Color.darkGray);
        div.setBounds(150, 60, 200, 30);

        ButtonGroup pointage = new ButtonGroup();
        pointage.add(here);
        pointage.add(mis);
        div.add(here);
        div.add(mis);
        this.add(div);
        employ = new JComboBox(dataCombox());
        employ.setSelectedItem("");
        employ.setBounds(30, 60, 100, 30);
        this.add(employ);

        JScrollPane tableScrollPane = createTablePanel();
        tableScrollPane.setBounds(35, 350, 920, 200);
        this.add(tableScrollPane);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateTime = new JSpinner(dateModel);

        //une autre date pour la recherche:

        SpinnerDateModel dateModel2 = new SpinnerDateModel();
        dateTimeSearch = new JSpinner(dateModel);





        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateTime, "dd//MM//yyyy HH:mm:ss");
        dateTime.setEditor(dateEditor);


        //SPinner date editor pour la recherche:

        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(dateTimeSearch,"dd//MM/yyy HH:mm:ss");
        dateTimeSearch.setEditor(dateEditor2);


        JPanel paneldate = new JPanel();
        paneldate.add(dateTime);
        paneldate.setSize(200, 50);
        paneldate.setBounds(10, 130, 200, 50);
        paneldate.setBackground(Color.darkGray);
        this.add(paneldate);

        //ajout du date search dans un panel

        JPanel panelDateSearch = new JPanel();
        panelDateSearch.add(dateTimeSearch);
        panelDateSearch.setSize(200, 50);
        panelDateSearch.setBounds(650, 200, 200, 50);
        panelDateSearch.setBackground(Color.darkGray);
        this.add(panelDateSearch);

        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String num = (String) employ.getSelectedItem();
                String presence = "";
                Date datetime = (Date) dateTime.getValue();
                Timestamp time = new Timestamp(datetime.getTime());
                if (here.isSelected() == true) presence = "Présent";
                else if (mis.isSelected() == true) presence = "Absent";
                int response = JOptionPane.showConfirmDialog(null, "Voulez-vous Ajouter?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(response == JOptionPane.YES_OPTION){
                    createPointage(time, num, presence);
                    JOptionPane.showMessageDialog(null, "Ajouter avec succès !");
                    clearData();
                    loadTableData();
                }
            }
        });

        modifBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    int response = JOptionPane.showConfirmDialog(null, "Voulez-vous Modifier?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        modifBtn.setVisible(false);
                        saveBtn.setVisible(true);
                        deleteBtn.setBounds(700, 130, 150, 30);
                        fillFieldsWithSelectedRowData(); // Remplir les champs avec les données sélectionnées
                        dateTime.setEnabled(false);
                        addBtn.setEnabled(false);
                        deleteBtn.setEnabled(false);
                        dateTime.setEnabled(false);
                    }
                }
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = (String) tableModel.getValueAt(selectedRow, 0);
                String num = (String) employ.getSelectedItem();
                String presence = "";
                if (here.isSelected() == true) presence = "Présent";
                else if (mis.isSelected() == true) presence = "Absent";

                modifierPointage(date, num, presence);
                JOptionPane.showMessageDialog(null, "Modification réussie !");
                loadTableData(); // Recharger les données après modification
                modifBtn.setVisible(true);
                saveBtn.setVisible(false);
                deleteBtn.setBounds(650, 130, 150, 30);
                dateTime.setEnabled(true);
                addBtn.setEnabled(true);
                deleteBtn.setEnabled(true);
                dateTime.setEnabled(true);
                employ.setSelectedItem("");
                clearData();

            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (selectedRow != -1) {
                    int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        String date = (String) tableModel.getValueAt(selectedRow, 0);
                        suppPointage(date);
                        JOptionPane.showMessageDialog(null, "Suppression réussie !");
                        loadTableData();
                        clearData();
                    }
                }
            }
        });

        researchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                rechercheEnTempsReel();
            }
    
            @Override
            public void removeUpdate(DocumentEvent e) {
                rechercheEnTempsReel();
                loadTableData();
            }
    
            @Override
            public void changedUpdate(DocumentEvent e) {
                rechercheEnTempsReel();
            }
    
            private void rechercheEnTempsReel() {
                String text = researchField.getText();
                researchPointageView(text); 
            }
        });

        dateTimeSearch.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e){
                System.out.println("OKKK");

            }
        });

        loadTableData();
    }

    public String [] dataCombox(){
        String [] data = null;
        try{
            ArrayList<String> dataList = new ArrayList<String>();
            EmployModel employModel = new EmployModel();
            ResultSet res = employModel.selectEmploye();
            if (res != null) {
                while (res.next()) {
                    dataList.add(res.getString("numemp"));
                }
            }
            data = new String[dataList.size()+1];

            int i = 0;

            while (i < dataList.size()) {
                data[i] = dataList.get(i);
                i++;
            }
            data[dataList.size()] ="";
            return data;

        } catch(SQLException e){
            e.printStackTrace();
        }

        return data;
    }

    private void createPointage(Timestamp date, String num, String presence){
        try{
            PointageModel pointage = new PointageModel(date, num, presence);
            pointage.addPointage();
        } catch(SQLException e){
            e.printStackTrace();
        } 
    }

    private JScrollPane createTablePanel(){
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Date");
        tableModel.addColumn("Numéro Employes");
        tableModel.addColumn("Pointage");
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = table.getSelectedRow();
            }
        });

        return new JScrollPane(table);
    }

    private void loadTableData() {
        tableModel.setRowCount(0); // Effacer les anciennes données
        System.out.println("Chargement des données...");

        try {
            PointageModel pointage = new PointageModel();
            ResultSet res = pointage.readPointage();

            if (res != null) {
                while (res.next()) {
                    Object[] row = new Object[3];
                    row[0] = res.getString("datepointage");
                    row[1] = res.getString("numemp");
                    row[2] = res.getString("pointage");
                    tableModel.addRow(row);
                }
            } else {
                System.out.println("Aucun résultat trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void suppPointage(String date){
        try{
            PointageModel pointageModel = new PointageModel(date);
            pointageModel.deletePointage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    private void modifierPointage(String date, String num, String presence){
        try{
            PointageModel pointageModel = new PointageModel(date, num, presence);
            pointageModel.updatePointage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void searchDatePointage(Timestamp dateTimestamp) {
        tableModel.setRowCount(0);
        try{
            // PointageModel pointage
        }
    }

    private void researchPointageView(String search){
        tableModel.setRowCount(0);
        try{
            PointageModel pointageModel = new PointageModel(search);
            ResultSet res = pointageModel.researchPointage();
            while (res.next()) {
                Object[] row = new Object[3];
                row[0] = res.getString("datepointage");
                row[1] = res.getString("numemp");
                row[2] = res.getString("pointage");
                tableModel.addRow(row);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void fillFieldsWithSelectedRowData() {
        if (selectedRow != -1) {
            String date = (String) tableModel.getValueAt(selectedRow, 0);
            String num = (String) tableModel.getValueAt(selectedRow, 1);
            String presence = (String) tableModel.getValueAt(selectedRow, 2);
    
            // Convertir la date de String à Timestamp en assurant le format correct
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(date + " 00:00:00");
                dateTime.setValue(parsedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
    
            // Sélectionner l'employé dans le JComboBox
            employ.setSelectedItem(num);
    
            // Sélectionner la présence dans les boutons radio
            if (presence.equals("Présent")) {
                here.setSelected(true);
            } else if (presence.equals("Absent")) {
                mis.setSelected(true);
            }
        }
    }

    public void updateCombox(){
        employ.removeAllItems();
        String [] data = dataCombox();
        for (String elem : data) {
            employ.addItem(elem);
            
        }
        employ.setSelectedItem("");
        here.setSelected(false);
        mis.setSelected(false);
        this.add(employ);


    }
    
    private void clearData(){
        here.setSelected(false);
        mis.setSelected(false);
        employ.setSelectedItem("");
        
    }

    // public void paintComponent(Graphics g){
    //     try {
    //         Image img = ImageIO.read(new File("./mety.jpg"));
    //         g.drawImage(img, 0, 0, this);
    //         } catch (IOException e) {
    //         e.printStackTrace();
    //         }
        
    // }
    
}
