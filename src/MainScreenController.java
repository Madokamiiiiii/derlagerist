import Models.Assignment;
import utils.AssignmentButtonPanel;
import utils.CSVHandler;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


// This class contains all the logic required for the MainScreen
public class MainScreenController {
    private int balance = 0;
    private List<Assignment> assignments;
    private List<AssignmentButtonPanel> assignmentButtonPanels;
    private Iterator<Assignment> iterator;
    private Assignment assignment;
    private Assignment selectedAssignment;

    private List<JButton> storage;

    public MainScreenController(List<AssignmentButtonPanel> assignmentButtonPanelList, List<JButton> storage) {
        assignments = CSVHandler.getAssignmentsFromCSV();
        iterator = assignments.iterator();
        this.assignmentButtonPanels = assignmentButtonPanelList;
        this.storage = storage;
    }

    // Gets the current assignment
    public Assignment getAssignment() {
        if (Objects.isNull(assignment)) {
            if (!iterator.hasNext()) {    // Start List anew
                iterator = assignments.iterator();
            }
            assignment = iterator.next();
        }
        return assignment;
    }

    public boolean moreAssignmentsAllowed() {
        return assignmentButtonPanels.stream().anyMatch(assignmentButtonPanel -> Objects.isNull(assignmentButtonPanel.getAssignment()));
    }

    // Adds the new assignment to the active assignments list
    public void addNewAssignment(Assignment assignment) {
        Optional<AssignmentButtonPanel> assignmentButtonPanel = assignmentButtonPanels.stream()
                .filter(asp -> Objects.isNull(asp.getAssignment()))
                .findFirst();
        assignmentButtonPanel.ifPresent(buttonPanel -> {
            buttonPanel.setAssignment(assignment);
            buttonPanel.getAssignmentButton()
                    .setText(assignment.toFormattedText());
        });
    }

    public void storeProduct() {

    }


    // Select one Assignment from the four. Unselect other if others are currently selected.
    // Unselect if already selected.
    // Do nothing if the panel doesn't hold an assignment.
    public void setSelectedAssignment(AssignmentButtonPanel selectedAbp) {
        if (selectedAbp.getAssignment() == selectedAssignment) {
            selectedAbp.getAssignmentButton().setBackground(null);
            selectedAssignment = null;
        } else if (Objects.nonNull(selectedAbp.getAssignment())) {
            for (AssignmentButtonPanel abp : assignmentButtonPanels) {
                if (abp.equals(selectedAbp)) {
                    abp.getAssignmentButton().setBackground(Color.green);
                    selectedAssignment = abp.getAssignment();
                } else {
                    abp.getAssignmentButton().setBackground(null);  // Reset button color
                }
            }
        }

    }
}
