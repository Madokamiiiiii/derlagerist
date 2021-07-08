package screens;

import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JFrame {
    Container c = this.getContentPane();

    public TitleScreen() {
        super();
        this.setTitle("Titelbildschirm");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
        JLabel title = new JLabel("Der Meister-Lagerist");
        title.setFont(new Font("Serif", Font.PLAIN, 30));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        c.add(title);
        c.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton startGameButton = new JButton("Spiel starten");
        startGameButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        startGameButton.addActionListener(e -> {
            this.setVisible(false);
            // new MainScreen().setVisible(true);
        });
        c.add(startGameButton);
        c.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton exitGame = new JButton("Spiel beenden");
        exitGame.setAlignmentX(JButton.CENTER_ALIGNMENT);
        exitGame.addActionListener(e -> System.exit(0));
        c.add(exitGame);
    }
}
