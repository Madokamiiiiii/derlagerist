package customui;

import models.Assignment;
import screens.MainScreenController;

import javax.swing.*;
import java.awt.*;

// This class creates a button pair for the assignments
public class AssignmentButtonPanel extends JPanel {
    private final AssignmentButton assignmentButton;
    private Assignment assignment;
    private MainScreenController controller;

    public AssignmentButtonPanel(MainScreenController controller) {
        super();
        this.setLayout(new FlowLayout());

        assignmentButton = new AssignmentButton("---");
        assignmentButton.addActionListener(e -> controller.setSelectedAssignment(this));
        this.add(assignmentButton);
        JButton assignmentReject = new JButton("X");
        assignmentReject.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Bist du sicher, dass du den Auftrag ablehnen willst? Die Belohnung wird als Vertragsstrafe vom Umsatz abgezogen.",
                    "Bestätigung",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                controller.rejectAssignment(this);
            }
        });
        assignmentReject.setBackground(Color.RED);
        this.add(assignmentReject);
    }

    public JButtonExtension getAssignmentButton() {
        return assignmentButton;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void reset() {
        assignment = null;
        assignmentButton.resetDesign();
        assignmentButton.setText("---");
    }

}
