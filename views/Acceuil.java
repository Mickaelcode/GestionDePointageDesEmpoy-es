package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JLabel;
import javax.swing.JPanel;
public class Acceuil extends JPanel{
    public Acceuil(){
        super();
        String  txt = "BIENVENUE CHER UTILISATEUR";
        JLabel label = new JLabel(txt);
        JPanel p = new JPanel();
        this.setLayout(null);
        p.setBounds(30,50,500,600);
        label.setBounds(350,130,500,50);
        Font police = new Font("Cursive", Font.BOLD, 19);
        label.setFont(police);
        label.setForeground(Color.WHITE);
        this.add(label);
        this.setBackground(Color.darkGray);
    }
    
    public void paintComponent(Graphics g){
        try {
            Image img = ImageIO.read(new File("./mety.jpg"));
            g.drawImage(img, 0, 0, this);
            } catch (IOException e) {
            e.printStackTrace();
            }
        
    }
    
}
