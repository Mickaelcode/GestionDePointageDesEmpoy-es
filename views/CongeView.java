package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import models.Conge;
import models.EmployModel;

public class CongeView extends JPanel {

    private JTextField researchField, numCongField, nbjrField;
    private JTextArea motifTextArea;
    private JButton addBtn, modifBtn, saveBtn, deleteBtn, rechButton;
    private JComboBox employ;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private JSpinner dateTimeFirst, dateTimeEnd;

    public CongeView() {
        JLabel title = new JLabel("CONGE DES EMPLOYES");
        JLabel numEmpLabel = new JLabel("Numero Employe:");
        JLabel numCongeLabel = new JLabel("Numero de congé:");
        JLabel rechLabel = new JLabel("Rechercher le jours restant:");
        JLabel nbrjrLabel = new JLabel("Nombre de jours:");
        JLabel datDemandeLabel = new JLabel("Date de Demande:");
        JLabel datRetourLabel = new JLabel("Date de retour:");
        JLabel motifLabel = new JLabel("Motifs:");
        this.setBackground(Color.darkGray);
        this.setLayout(null);

        researchField = new JTextField(15);
        numCongField = new JTextField(15);
        nbjrField = new JTextField(15);
        motifTextArea = new JTextArea();
        motifTextArea.setBounds(200, 130, 200, 50);
        motifLabel.setBounds(200, 100, 300, 30);
        numCongField.setBounds(30, 60, 130, 30);
        nbjrField.setBounds(200, 60, 50, 30);
        researchField.setBounds(650, 60, 250, 30);
        rechLabel.setBounds(650, 30, 250, 30);
        this.add(researchField);
        this.add(numCongField);
        this.add(motifTextArea);
        this.add(rechLabel);
        this.add(nbjrField);
        this.add(motifLabel);

        addBtn = new JButton("AJOUTER");
        addBtn.setBounds(350, 250, 100, 30);
        modifBtn = new JButton("MODIFIER");
        modifBtn.setBounds(500, 250, 100, 30);
        saveBtn = new JButton("ENREGISTRER");
        saveBtn.setBounds(500, 250, 150, 30);
        saveBtn.setVisible(false);
        deleteBtn = new JButton("SUPPRIMER");
        deleteBtn.setBounds(650, 250, 150, 30);

        rechButton = new JButton();
        rechButton.setBackground(Color.BLUE);
        rechButton.setBounds(894, 60, 30, 30);

        this.add(addBtn);
        this.add(modifBtn);
        this.add(saveBtn);
        this.add(deleteBtn);
        this.add(rechButton);

        addBtn.setBackground(Color.BLUE);
        addBtn.setForeground(Color.white);
        modifBtn.setBackground(Color.GREEN);
        modifBtn.setForeground(Color.white);
        saveBtn.setBackground(Color.GREEN);
        saveBtn.setForeground(Color.white);
        deleteBtn.setBackground(Color.red);
        deleteBtn.setForeground(Color.white);

        title.setBounds(390, 0, 300, 30);
        numCongeLabel.setBounds(30, 30, 300, 30);
        numEmpLabel.setBounds(30, 100, 300, 30);
        nbrjrLabel.setBounds(200, 30, 300, 30);
        datDemandeLabel.setBounds(30, 160, 300, 30);
        datRetourLabel.setBounds(30, 220, 300, 30);
        Font police = new Font("Cursive", Font.BOLD, 19);
        title.setFont(police);
        title.setForeground(Color.white);
        numCongeLabel.setForeground(Color.white);
        numEmpLabel.setForeground(Color.white);
        nbrjrLabel.setForeground(Color.white);
        datDemandeLabel.setForeground(Color.white);
        datRetourLabel.setForeground(Color.white);
        motifLabel.setForeground(Color.white);
        rechLabel.setForeground(Color.white);

        this.add(numCongeLabel);
        this.add(numEmpLabel);
        this.add(datDemandeLabel);
        this.add(datRetourLabel);
        this.add(title);
        this.add(nbrjrLabel);

        employ = new JComboBox(dataCombox());
        employ.setBounds(30, 130, 100, 30);
        employ.setSelectedItem("");
        this.add(employ);

        JScrollPane tableScrollPane = createTablePanel();
        tableScrollPane.setBounds(35, 350, 920, 200);
        this.add(tableScrollPane);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        SpinnerDateModel dateModele = new SpinnerDateModel();
        dateTimeFirst = new JSpinner(dateModel);
        dateTimeEnd = new JSpinner(dateModele);

        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateTimeFirst, "dd//MM//yyyy ");
        JSpinner.DateEditor dateEditore = new JSpinner.DateEditor(dateTimeEnd, "dd//MM//yyyy ");
        dateTimeFirst.setEditor(dateEditor);
        dateTimeEnd.setEditor(dateEditore);

        JPanel paneldate = new JPanel();
        JPanel paneldateR = new JPanel();
        paneldate.add(dateTimeFirst);
        paneldateR.add(dateTimeEnd);
        paneldate.setSize(90, 30);
        paneldateR.setSize(90, 30);
        paneldate.setBounds(-10, 190, 200, 30);
        paneldateR.setBounds(-10, 250, 200, 30);
        paneldate.setBackground(Color.darkGray);
        paneldateR.setBackground(Color.darkGray);
        this.add(paneldate);
        this.add(paneldateR);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeroConge = numCongField.getText();
                String numeroEmploye = (String) employ.getSelectedItem();
                String motifConge = motifTextArea.getText();
                int nbjr = Integer.parseInt(nbjrField.getText());

                java.util.Date utilDateDemande = (java.util.Date) dateTimeFirst.getValue();
                java.util.Date utilDateRetour = (java.util.Date) dateTimeEnd.getValue();
                java.sql.Date sqlDateDemande = new java.sql.Date(utilDateDemande.getTime());
                java.sql.Date sqlDateRetour = new java.sql.Date(utilDateRetour.getTime());
                Timestamp timeFirst = new Timestamp(sqlDateDemande.getTime());
                Timestamp timeEnd = new Timestamp(sqlDateRetour.getTime());
                int response = JOptionPane.showConfirmDialog(null, "Voulez-vous Ajouter?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    String validate = createConge(numeroConge, numeroEmploye, motifConge, nbjr, timeFirst, timeEnd);
                    if (validate == "VITA") {
                        JOptionPane.showMessageDialog(null, "Ajouter avec succès !");
                        loadTableData();
                        clear();

                    }

                    else if (validate == "Max") {
                        int days = researchCongeView(numeroEmploye);
                        if (days == 0) {
                            JLabel txt = new JLabel("Cette employé n' a plus de jours pour congé");
                            JDialog dialog = new JDialog();
                            dialog.setLayout(null);
                            txt.setBounds(30, 30, 400, 100);
                            dialog.setTitle("****NOTIFICATION****");
                            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            dialog.setLocationRelativeTo(null);
                            dialog.setSize(400, 200);
                            dialog.setLocation(800, 200);
                            dialog.add(txt);
                            dialog.setVisible(true);

                            // JOptionPane.showMessageDialog(null, "Cette employé n' a plus de jours pour
                            // congé");
                        } else
                            JOptionPane.showMessageDialog(null,
                                    "Cette employe n'a plus que " + days + " jours  pour congé");
                        clear();
                    }
                }
            }

        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numconge = numCongField.getText();
                String num = (String) employ.getSelectedItem();
                String motif = motifTextArea.getText();
                java.util.Date utilDateDemande = (java.util.Date) dateTimeFirst.getValue();
                java.util.Date utilDateRetour = (java.util.Date) dateTimeEnd.getValue();
                java.sql.Date sqlDateDemande = new java.sql.Date(utilDateDemande.getTime());
                java.sql.Date sqlDateRetour = new java.sql.Date(utilDateRetour.getTime());
                Timestamp timeFirst = new Timestamp(sqlDateDemande.getTime());
                Timestamp timeEnd = new Timestamp(sqlDateRetour.getTime());
                int nbjr = Integer.parseInt(nbjrField.getText());

                modifierConge(numconge, num, motif, nbjr, timeFirst, timeEnd);
                JOptionPane.showMessageDialog(null, "Modification réussie !");
                loadTableData(); // Recharger les données après modification
                modifBtn.setVisible(true);
                saveBtn.setVisible(false);
                deleteBtn.setBounds(650, 250, 150, 30);
                numCongField.setEnabled(true);
                deleteBtn.setEnabled(true);
                addBtn.setEnabled(true);
                clear();

            }
        });

        modifBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    int response = JOptionPane.showConfirmDialog(null, "Voulez-vous Modifier?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        modifBtn.setVisible(false);
                        saveBtn.setVisible(true);
                        deleteBtn.setBounds(700, 250, 150, 30);
                        fillFieldsWithSelectedRowData(); // Remplir les champs avec les données sélectionnées
                        numCongField.setEnabled(false);
                        deleteBtn.setEnabled(false);
                        addBtn.setEnabled(false);
                    }
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ?",
                            "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        String numconge = (String) tableModel.getValueAt(selectedRow, 0);
                        suppConge(numconge);
                        JOptionPane.showMessageDialog(null, "Suppression réussie !");
                        loadTableData();
                    }
                }
            }
        });

        rechButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String search = researchField.getText();

                int days = researchCongeView(search);

                if (days == 0)
                    JOptionPane.showMessageDialog(null, "Cette employé n' a plus de jours pour congé");
                else if (days == 30)
                    JOptionPane.showMessageDialog(null, "Cette employé n' a pas encore fait de congé");
                else
                    JOptionPane.showMessageDialog(null,
                            "Cette employe a encore " + days + " jours de jours pour congé");
                clear();
            }
        });

        loadTableData();

    }

    private String[] dataCombox() {
        String[] data = null;
        try {
            ArrayList<String> dataList = new ArrayList<String>();
            EmployModel employModel = new EmployModel();
            ResultSet res = employModel.selectEmploye();
            if (res != null) {
                while (res.next()) {
                    dataList.add(res.getString("numemp"));
                }
            }
            data = new String[dataList.size() + 1];

            int i = 0;

            while (i < dataList.size()) {
                data[i] = dataList.get(i);
                i++;
            }
            data[dataList.size()] = "";
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Numéro congé");
        tableModel.addColumn("Numéro Employes");
        tableModel.addColumn("Motifs");
        tableModel.addColumn("Nombre de jours");
        tableModel.addColumn("Date de demande");
        tableModel.addColumn("Date de retour");
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
            Conge pointage = new Conge();
            ResultSet res = pointage.readConge();

            if (res != null) {
                while (res.next()) {
                    Object[] row = new Object[6];
                    row[0] = res.getString("numconge");
                    row[1] = res.getString("numemp");
                    row[2] = res.getString("motif");
                    row[3] = res.getString("nbrjr");
                    row[4] = res.getString("datedemande");
                    row[5] = res.getString("dateretour");
                    tableModel.addRow(row);
                }
            } else {
                System.out.println("Aucun résultat trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void suppConge(String numConge) {
        try {
            Conge conge = new Conge(numConge);
            conge.deleteConge();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modifierConge(String numConge, String numEmp, String motif, int nbJours, Timestamp strDateDemande,
            Timestamp strDateretour) {
        try {
            Conge conge = new Conge(numConge, numEmp, motif, nbJours, strDateDemande, strDateretour);
            conge.updateConge();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String createConge(String numConge, String numEmp, String motif, int nbJours, Timestamp timeFirst,
            Timestamp timeEnd) {
        String answer = "";
        String send = "";
        try {
            Conge conge = new Conge(numConge, numEmp, motif, nbJours, timeFirst, timeEnd);
            answer = conge.addConge();
            if (answer == "Maximum")
                send = "Max";
            else if (answer == "VITA")
                send = "VITA";

            return send;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return send;
    }

    private int researchCongeView(String search) {
        int days = 0;
        try {
            Conge c = new Conge(search, 2);
            days = c.rechNbJr();
            return days;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return days;
    }

    private void fillFieldsWithSelectedRowData() {
        if (selectedRow != -1) {
            // Récupérer les valeurs de la ligne sélectionnée
            String numConge = (String) tableModel.getValueAt(selectedRow, 0);
            String numEmp = (String) tableModel.getValueAt(selectedRow, 1);
            String motif = (String) tableModel.getValueAt(selectedRow, 2);
            String jours = (String) tableModel.getValueAt(selectedRow, 3);
            String dateDemandeStr = (String) tableModel.getValueAt(selectedRow, 4);
            String dateRetourStr = (String) tableModel.getValueAt(selectedRow, 5);

            // Convertir les chaînes de date en objets java.util.Date
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dateDemande = dateFormat.parse(dateDemandeStr);
                java.util.Date dateRetour = dateFormat.parse(dateRetourStr);

                // Mettre à jour les champs de date
                dateTimeFirst.setValue(dateDemande);
                dateTimeEnd.setValue(dateRetour);
            } catch (ParseException e) {
                e.printStackTrace();

            }
            // Mettre à jour les autres champs
            numCongField.setText(numConge);
            nbjrField.setText(jours);
            employ.setSelectedItem(numEmp);
            motifTextArea.setText(motif);
        }

    }

    private void clear() {
        numCongField.setText("");
        employ.setSelectedItem("");
        nbjrField.setText("");
        motifTextArea.setText("");
        researchField.setText("");
    }

    public void updateCombox() {
        employ.removeAllItems();
        String[] data = dataCombox();
        for (String elem : data) {
            employ.addItem(elem);

        }
        employ.setSelectedItem("");
        this.add(employ);
    }

    // public void paintComponent(Graphics g){
    // try {
    // Image img = ImageIO.read(new File("./mety.jpg"));
    // g.drawImage(img, 0, 0, this);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }

    // }
}