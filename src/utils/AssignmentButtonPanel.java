package utils;

import javax.swing.*;
import java.awt.*;

// This class creates a button pair for the assignments
// TODO: Add ButtonAction
public class AssignmentButtonPanel extends JPanel {
    private final JButton assignmentButton;

    public AssignmentButtonPanel() {
        super();
        this.setLayout(new FlowLayout());

        assignmentButton = new JButton();
        assignmentButton.setText("---");
        this.add(assignmentButton);
        JButton assignmentReject = new JButton("X");
        this.add(assignmentReject);
    }

    public JButton getAssignmentButton() {
        return assignmentButton;
    }
}
