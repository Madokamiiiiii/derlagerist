import Models.Assignment;
import utils.AssignmentButtonPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends JFrame{
    private Container c = this.getContentPane();
    private MainScreenController controller;

    public MainScreen() throws IOException {
        super();
        this.setSize(600, 650);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setTitle("Der Meister-Lagerist");

        c.setLayout(new BorderLayout());

        // Top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createRigidArea(new Dimension(5, 15)));

        JLabel balanceLabel = new JLabel("Umsatz: ");
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        topPanel.add(balanceLabel);
        JLabel balance = new JLabel("0");
        balance.setFont(new Font("Serif", Font.PLAIN, 30));
        topPanel.add(balance);
        topPanel.add(Box.createHorizontalGlue());

        JLabel message = new JLabel();
        message.setFont(new Font("Serif", Font.PLAIN, 25));
        message.setForeground(Color.RED);
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
        newAssignment.addActionListener(e -> {
            if (controller.moreAssignmentsAllowed()) {
                new NewAssignmentDialog(controller);
            } else {
                message.setText("Du hast bereits 4 aktive Aufträge!");
            }
        });
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(newAssignment);
        bottomPanel.add(Box.createHorizontalStrut(5));

        c.add(bottomPanel, BorderLayout.SOUTH);


        // Right
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createHorizontalStrut(5));

        List<AssignmentButtonPanel> assignmentButtonPanelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AssignmentButtonPanel abp = new AssignmentButtonPanel();
            abp.getAssignmentButton().addActionListener(e -> controller.setSelectedAssignment(abp));
            rightPanel.add(abp);
            assignmentButtonPanelList.add(abp);
        }

        c.add(rightPanel, BorderLayout.EAST);


        // Center
        List<JButton> storage = new ArrayList<>();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        JPanel frontStoragePanel = new JPanel();
        frontStoragePanel.setLayout(new BoxLayout(frontStoragePanel, BoxLayout.Y_AXIS));
        frontStoragePanel.add(new JLabel("Vorne"));

        JPanel frontStorage = new JPanel(new GridLayout(5, 2));
        for (int i = 0; i < 10; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setBackground(Color.gray);
            button.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            button.addActionListener(null); // TODO Add Action Listener
            frontStorage.add(button);
            storage.add(button);
        }

        frontStoragePanel.add(frontStorage);
        centerPanel.add(frontStoragePanel);

        centerPanel.add(Box.createHorizontalStrut(10));

        JPanel backStoragePanel = new JPanel();
        backStoragePanel.setLayout(new BoxLayout(backStoragePanel, BoxLayout.Y_AXIS));
        JLabel backLabel = new JLabel("Hinten");
        backStoragePanel.add(backLabel);

        JPanel backStorage = new JPanel(new GridLayout(5, 2));
        for (int i = 0; i < 10; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setBackground(Color.gray);
            button.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            button.addActionListener(null); // TODO Add Action Listener
            backStorage.add(button);
            storage.add(button);
        }

        backStoragePanel.add(backStorage);
        centerPanel.add(backStoragePanel);

        c.add(centerPanel, BorderLayout.CENTER);

        controller  = new MainScreenController(assignmentButtonPanelList, storage);

    }
}
