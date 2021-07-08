package customui;

import models.Assignment;
import screens.MainScreenController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

// This class creates a button pair for the assignments
public class AssignmentButtonPanel extends JPanel {
    private final AssignmentButton assignmentButton;
    private Assignment assignment;

    public AssignmentButtonPanel(MainScreenController controller) {
        super();
        this.setLayout(new FlowLayout());

        assignmentButton = new AssignmentButton("---");
        assignmentButton.addActionListener(e -> controller.setSelectedAssignment(this));
        this.add(assignmentButton);
        JButton assignmentReject = new JButton("X");
        assignmentReject.addActionListener(e -> {
            if (Objects.nonNull(assignment)) {
                int result = JOptionPane.showConfirmDialog(this,
                        "<html>Bist du sicher, dass du den Auftrag ablehnen willst?<br>" +
                                "Die Belohnung wird als Vertragsstrafe vom Umsatz abgezogen.</html>",
                        "Bestätigung",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    controller.rejectAssignment(this);
                }
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
