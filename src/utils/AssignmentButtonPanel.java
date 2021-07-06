package utils;

import Models.Assignment;

import javax.swing.*;
import java.awt.*;

// This class creates a button pair for the assignments
public class AssignmentButtonPanel extends JPanel {
    private final JButton assignmentButton;
    private Assignment assignment;

    public AssignmentButtonPanel() {
        super();
        this.setLayout(new FlowLayout());

        assignmentButton = new JButton();
        assignmentButton.setText("---");
        this.add(assignmentButton);
        JButton assignmentReject = new JButton("X");
        // TODO: Add Reject method
        assignmentReject.setBackground(Color.RED);
        this.add(assignmentReject);
    }

    public JButton getAssignmentButton() {
        return assignmentButton;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

}
