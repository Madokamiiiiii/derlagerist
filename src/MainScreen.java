import utils.AssignmentButtonPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainScreen extends JFrame{
    Container c = this.getContentPane();


    public MainScreen() throws IOException {
        super();
        this.setSize(500, 500);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setTitle("Der Meister-Lagerist");
        MainScreenController controller = new MainScreenController();

        c.setLayout(new BorderLayout());

        // Top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createRigidArea(new Dimension(5, 15)));

        JLabel balanceLabel = new JLabel("Umsatz");
        balanceLabel.setAlignmentX(LEFT_ALIGNMENT);
        topPanel.add(balanceLabel);

        JLabel message = new JLabel();
        message.setAlignmentX(LEFT_ALIGNMENT);
        topPanel.add(message);

        c.add(topPanel, BorderLayout.NORTH);

        // Left
        JPanel leftPanel = new JPanel(new FlowLayout());
        topPanel.add(Box.createRigidArea(new Dimension(5, 10)));
        JButton balanceButton = new JButton("Bilanz");
        balanceButton.addActionListener(e -> {
            // TODO: Add balance table
        });
        leftPanel.add(balanceButton);
        leftPanel.add(Box.createHorizontalGlue());
        c.add(leftPanel, BorderLayout.WEST);

        // Bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        Image trashIcon = ImageIO.read(this.getClass().getResource("assets/trashcan.png")).getScaledInstance(60, 90, Image.SCALE_SMOOTH);
        JButton trashButton = new JButton(new ImageIcon(trashIcon));
        trashButton.setBorder(BorderFactory.createEmptyBorder());
        trashButton.setContentAreaFilled(false);
        trashButton.addActionListener(e -> {
            // TODO: Add functionality
            System.out.println("Trash Button clicked");
        });

        bottomPanel.add(trashButton);
        JButton newAssignment = new JButton("Neuer Auftrag");
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(newAssignment);
        bottomPanel.add(Box.createHorizontalStrut(5));

        c.add(bottomPanel, BorderLayout.SOUTH);


        // Right
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createHorizontalStrut(5));

        for (int i = 0; i < 4; i++) {
            rightPanel.add(new AssignmentButtonPanel());
        }

        c.add(rightPanel, BorderLayout.EAST);

        // Center
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        for (int i = 0; i < 10; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setBackground(Color.gray);
            button.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            button.addActionListener(null); // TODO Add Action Listener
            centerPanel.add(button);
        }
        c.add(centerPanel, BorderLayout.CENTER);
    }
}
