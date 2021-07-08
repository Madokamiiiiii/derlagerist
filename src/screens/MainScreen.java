package screens;

import customui.AssignmentButtonPanel;
import customui.MoneyLabel;
import customui.StorageButton;
import models.Assignment;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainScreen extends JFrame {
    private final MainScreenController controller;

    public MainScreen() throws IOException {
        super();
        this.setSize(1000, 650);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Der Meister-Lagerist");

        controller = new MainScreenController();

        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());

        // Top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createRigidArea(new Dimension(5, 15)));

        JLabel balanceLabel = new JLabel("Kontostand: ");
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        topPanel.add(balanceLabel);
        MoneyLabel balance = new MoneyLabel("0");
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
        balanceButton.addActionListener(e -> controller.openBalanceTable());
        leftPanel.add(balanceButton);
        leftPanel.add(Box.createHorizontalGlue());
        c.add(leftPanel, BorderLayout.WEST);

        // Bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(Box.createHorizontalStrut(10));
        Image trashIcon = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/trashcan.png"))).getScaledInstance(60, 90, Image.SCALE_SMOOTH);
        JButton trashButton = new JButton(new ImageIcon(trashIcon));
        trashButton.setFocusPainted(false);
        trashButton.setBorder(BorderFactory.createEmptyBorder());
        trashButton.setContentAreaFilled(false);
        trashButton.addActionListener(e -> controller.trashProduct());
        bottomPanel.add(trashButton);
        JButton newAssignment = new JButton("Neuer Auftrag");
        newAssignment.addActionListener(e -> {
            if (controller.moreAssignmentsAllowed()) {
                message.setText("");
                Assignment assignment = controller.getCurrentAssignment();
                if (JOptionPane.showConfirmDialog(this,
                        "<html>Der neue Auftrag: <br>"
                                + assignment.toFormattedText() + "</html>",
                        "Neuer Auftrag",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    controller.addNewAssignment(assignment);
                }

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
            AssignmentButtonPanel abp = new AssignmentButtonPanel(controller);
            rightPanel.add(abp);
            assignmentButtonPanelList.add(abp);
        }

        c.add(rightPanel, BorderLayout.EAST);


        // Center
        List<StorageButton> storage = new ArrayList<>();

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        JPanel frontStoragePanel = new JPanel();
        frontStoragePanel.setLayout(new BoxLayout(frontStoragePanel, BoxLayout.Y_AXIS));
        frontStoragePanel.add(new JLabel("Vorne"));

        JPanel frontStorage = new JPanel(new GridLayout(5, 2));
        for (int i = 0; i < 10; i++) {
            StorageButton button = new StorageButton("Leer");
            button.addActionListener(e -> controller.manageStorage(button));
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
            StorageButton button = new StorageButton("Leer");
            button.addActionListener(e -> controller.manageStorage(button));
            backStorage.add(button);
            storage.add(button);
        }

        backStoragePanel.add(backStorage);
        centerPanel.add(backStoragePanel);

        c.add(centerPanel, BorderLayout.CENTER);

        controller.setAssignmentButtonPanels(assignmentButtonPanelList);
        controller.setStorage(storage);
        controller.setMessage(message);
        controller.setBalanceLabel(balance);
    }
}
