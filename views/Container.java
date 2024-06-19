package views;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Container extends JFrame {
    private JMenuItem acceuilMenu, employ, pointage, conge, fiche;
    private EmployeView employeView;
    private PointageView pointageView;
    private CongeView vCongeView;
    private Fiche file;

    public Container() {
        super();
        this.setSize(1000, 800);
        this.setResizable(false);
        this.setTitle("****GESTIONS DE POINTAGE DES EMPLOYES****");
        this.setLayout(new BorderLayout());
        PanelNorth w = new PanelNorth();
        this.add(w, BorderLayout.NORTH);
        Acceuil acceuil = new Acceuil();
        employeView = new EmployeView();
        pointageView = new PointageView();
        vCongeView = new CongeView();
        // file = new Fiche();
        JPanel div = new JPanel();
        div.setLayout(new CardLayout());

        div.add(acceuil, "acceuil");
        div.add(employeView, "employ");
        div.add(pointageView, "pointage");
        div.add(vCongeView, "conge");
        // div.add(file,"file");
        this.add(div, BorderLayout.CENTER);
        CardLayout cardLayout = (CardLayout) div.getLayout();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        acceuilMenu = w.getAcceuil();
        employ = w.getEmploy();
        pointage = w.getPointage();
        conge = w.getConge();
        fiche = w.getFiche();

        acceuilMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(div, "acceuil");

            }
        });
        employ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(div, "employ");
                employeView.loadTableData();

            }
        });

        pointage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(div, "pointage");
                pointageView.updateCombox();

            }
        });

        conge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(div, "conge");
                vCongeView.updateCombox();
            }
        });
        fiche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // cardLayout.show(div, "file");
                file = new Fiche();
                file.updateCombox();

            }
        });
    }

}