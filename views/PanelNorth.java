package views;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PanelNorth extends JPanel {
    private    JMenuItem acceuil,employ,pointage,conge,fiche;
    public PanelNorth(){
        super();
        
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        acceuil = new JMenuItem("Acceuil");
        employ = new JMenuItem("Employ");
        pointage = new JMenuItem("Pointage");
        conge = new JMenuItem("Conge");
        fiche = new JMenuItem("Fiche de paye");
        menu.add(acceuil);
        menu.add(employ);
        menu.add(pointage);
        menu.add(conge);
        menu.add(fiche);
        mb.add(menu);
        
        // JLabel label = new JLabel("Gestion des employes");
        // this.add(label);
        this.add(mb);
        this.setBackground(Color.GRAY);
    }

    public JMenuItem getAcceuil(){
        return acceuil;
    }
    public JMenuItem getEmploy(){
        return employ;
    } public JMenuItem getPointage(){
        return pointage;
    } public JMenuItem getConge(){
        return conge;
    } public JMenuItem getFiche(){
        return fiche;
    }
}
