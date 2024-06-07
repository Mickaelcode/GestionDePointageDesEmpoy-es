package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelWest extends JPanel {
    public PanelWest() {
        super();
        JButton emploiBtn = new JButton("Employers");
        JButton pointageBtn = new JButton("Pointages");
        JButton congeBtn = new JButton("Conges");

        Dimension buttonSize = new Dimension(150, 30);
        emploiBtn.setPreferredSize(buttonSize);
        pointageBtn.setPreferredSize(buttonSize);
        congeBtn.setPreferredSize(buttonSize);

        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.yellow);

        JPanel panel = new JPanel();
        panel.setBackground(Color.yellow);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);  // Add padding between buttons

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emploiBtn, gbc);

        gbc.gridy = 1;
        panel.add(pointageBtn, gbc);

        gbc.gridy = 2;
        panel.add(congeBtn, gbc);

        this.add(panel);
    }
}
