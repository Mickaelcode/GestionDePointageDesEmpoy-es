package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import models.EmployModel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EmployeView extends JPanel {
    private JTextField numEmp;
    private JTextField name;
    private JTextField firstName;
    private JTextField post;
    private JTextField conge;
    private JTextField researchField;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private JButton modifBtn;
    private JButton saveBtn;

    public EmployeView() {
        super();
        JLabel title = new JLabel("FORMULAIRE DES EMPLOYERS");
        JLabel numLabel = new JLabel("Numero Employe:");
        JLabel nameLabel = new JLabel("Nom:");
        JLabel firstnameLabel = new JLabel("Prénoms");
        JLabel postLabel = new JLabel("Poste :");
        JLabel freeLabel = new JLabel("Salaire :");
        JLabel rechLabel = new JLabel("Recherche :");
        this.setBackground(Color.darkGray);
        this.setLayout(null);

        numEmp = new JTextField(15);
        name = new JTextField(15);
        firstName = new JTextField(15);
        post = new JTextField(15);
        conge = new JTextField(15);
        researchField = new JTextField(15);

        JButton addBtn = new JButton("AJOUTER");
        addBtn.setBounds(350, 250, 100, 30);
        modifBtn = new JButton("MODIFIER");
        modifBtn.setBounds(500, 250, 100, 30);
        saveBtn = new JButton("ENREGISTRER");
        saveBtn.setBounds(500, 250, 150, 30);
        saveBtn.setVisible(false);
        JButton deleteBtn = new JButton("SUPPRIMER");
        deleteBtn.setBounds(650, 250, 150, 30);

        JButton rechButton = new JButton();
        rechButton.setBackground(Color.BLUE);
        rechButton.setBounds(894,60,30,30);
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

        title.setBounds(350, 0, 350, 30);
        numLabel.setBounds(30, 30, 300, 30);
        nameLabel.setBounds(30, 100, 300, 30);
        firstnameLabel.setBounds(30, 160, 300, 30);
        postLabel.setBounds(350, 30, 300, 30);
        freeLabel.setBounds(350, 100, 300, 30);
        rechLabel.setBounds(650,30, 300, 30);
        Font police = new Font("Cursive", Font.BOLD, 19);
        title.setFont(police );
        title.setForeground(Color.white);
        numLabel.setForeground(Color.white);
        nameLabel.setForeground(Color.white);
        firstnameLabel.setForeground(Color.white);
        postLabel.setForeground(Color.white);
        freeLabel.setForeground(Color.white);
        rechLabel.setForeground(Color.white);



        this.add(title);
        this.add(numLabel);
        this.add(nameLabel);
        this.add(firstnameLabel);
        this.add(postLabel);
        this.add(freeLabel);
        this.add(rechLabel);

        numEmp.setBounds(30, 60, 200, 30);
        name.setBounds(30, 130, 200, 30);
        firstName.setBounds(30, 190, 200, 30);
        post.setBounds(350, 60, 200, 30);
        conge.setBounds(350, 130, 200, 30);
        researchField.setBounds(650,60, 250, 30);

        this.add(numEmp);
        this.add(name);
        this.add(firstName);
        this.add(post);
        this.add(conge);
        this.add(researchField);

        // Ajout du JScrollPane avec JTable
        JScrollPane tableScrollPane = createTablePanel();
        tableScrollPane.setBounds(35, 350, 920, 200);
        this.add(tableScrollPane);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numEmpValue = numEmp.getText();
                String nameValue = name.getText();
                String firstNameValue = firstName.getText();
                String postValue = post.getText();
                int congeValue = Integer.parseInt(conge.getText());
                int response = JOptionPane.showConfirmDialog(null, "Voulez-vous Ajouter?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    createEmploy(numEmpValue, nameValue, firstNameValue, postValue, congeValue);
                    loadTableData(); // Recharger les données après ajout
                    clearEmploy();
                }
            }
        });

        
        modifBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    int response = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment modifier ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        numEmp.setText((String) tableModel.getValueAt(selectedRow, 0));
                        name.setText((String) tableModel.getValueAt(selectedRow, 1));
                        firstName.setText((String) tableModel.getValueAt(selectedRow, 2));
                        post.setText((String) tableModel.getValueAt(selectedRow, 3));
                        conge.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
                        modifBtn.setVisible(false);
                        saveBtn.setVisible(true);
                        deleteBtn.setBounds(700, 250, 150, 30);
                        addBtn.setEnabled(false);
                        deleteBtn.setEnabled(false);
                        numEmp.setEnabled(false);
                    }
                }
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numEmpValue = numEmp.getText();
                String nameValue = name.getText();
                String firstNameValue = firstName.getText();
                String postValue = post.getText();
                int congeValue = Integer.parseInt(conge.getText());

                modifEmploy(numEmpValue, nameValue, firstNameValue, postValue, congeValue);
                JOptionPane.showMessageDialog(null, "Modification réussie !");
                loadTableData(); // Recharger les données après modification
                modifBtn.setVisible(true);
                saveBtn.setVisible(false);
                deleteBtn.setBounds(650, 250, 150, 30);
                clearEmploy();
                addBtn.setEnabled(true);
                deleteBtn.setEnabled(true);
                numEmp.setEnabled(true);
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        String numEmpValue = (String) tableModel.getValueAt(selectedRow, 0);
                        String m =suppEmploy(numEmpValue);
                        if (m =="ok") JOptionPane.showMessageDialog(null, "Suppression réussie !");
                        else JOptionPane.showMessageDialog(null, "Peut pas etre supprimé désolé !");
                        loadTableData(); // Recharger les données après suppression
                        clearEmploy();
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
            }
    
            @Override
            public void changedUpdate(DocumentEvent e) {
                rechercheEnTempsReel();
            }
    
            private void rechercheEnTempsReel() {
                String text = researchField.getText();
                rechercherEmploy(text); 
            }
        });

        // Charger les données initiales de la table
        loadTableData();
    }

    private void createEmploy(String numEmp, String name, String firstname, String post, int conge) {
        try {
            EmployModel employee = new EmployModel(numEmp, name, firstname, post, conge);
            employee.addEmploy();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void modifEmploy(String numEmp, String name, String firstname, String post, int conge) {
        try {
            EmployModel employee = new EmployModel(numEmp, name, firstname, post, conge);
            employee.updateEmploye();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private String suppEmploy(String numEmp) {
        try {
            EmployModel employee = new EmployModel(numEmp);
            String m =employee.deleteEmploy();
            return m;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return "null";
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Numéro");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Poste");
        tableModel.addColumn("Salaire");

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = table.getSelectedRow();
            }
        });

        return new JScrollPane(table);
    }

    public void loadTableData() {
        tableModel.setRowCount(0); // Effacer les anciennes données
        System.out.println("Chargement des données...");

        try {
            EmployModel employee = new EmployModel();
            ResultSet res = employee.selectEmploye();

            if (res != null) {
                while (res.next()) {
                    Object[] row = new Object[5];
                    row[0] = res.getString("Numemp");
                    row[1] = res.getString("Nom");
                    row[2] = res.getString("Prenom");
                    row[3] = res.getString("Poste");
                    row[4] = res.getInt("Salaire");
                    tableModel.addRow(row);
                }
            } else {
                System.out.println("Aucun résultat trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearEmploy(){
        numEmp .setText("");;
        name .setText("");;
        firstName .setText("");;
        post .setText("");;
        conge .setText("");;
        researchField.setText("");
    }

    private void rechercherEmploy(String rech){
        tableModel.setRowCount(0); // Effacer les anciennes données
        try {
            EmployModel employee = new EmployModel(rech);
            ResultSet res = employee.rechercheEmploy();
            while (res.next()) {
                Object[] row = new Object[5];
                row[0] = res.getString("NumEmp");
                row[1] = res.getString("Nom");
                row[2] = res.getString("Prenom");
                row[3] = res.getString("Poste");
                row[4] = res.getDouble("Salaire");
                tableModel.addRow(row);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
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
