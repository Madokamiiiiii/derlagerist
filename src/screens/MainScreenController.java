package screens;

import models.Assignment;
import customui.AssignmentButtonPanel;
import utils.BalanceHandler;
import utils.CSVHandler;
import customui.JButtonExtension;
import customui.StorageButton;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


// This class contains all the logic required for the MainScreen
public class MainScreenController {
    private BalanceHandler balanceHandler;

    private int balance = 0;
    private JLabel balanceLabel;
    private List<Assignment> assignments;
    private List<AssignmentButtonPanel> assignmentButtonPanels;
    private Iterator<Assignment> iterator;
    private Assignment nextAssignment;
    private Assignment selectedAssignment;
    private StorageButton selectedStorage;
    private JLabel message;
    private List<StorageButton> storage;

    public MainScreenController() {
        assignments = CSVHandler.getAssignmentsFromCSV();
        iterator = assignments.iterator();
        balanceHandler = new BalanceHandler();
    }

    public void setBalanceLabel(JLabel balanceLabel) {
        this.balanceLabel = balanceLabel;
    }

    public void setAssignmentButtonPanels(List<AssignmentButtonPanel> assignmentButtonPanels) {
        this.assignmentButtonPanels = assignmentButtonPanels;
    }

    public void setMessage(JLabel message) {
        this.message = message;
    }

    public void setStorage(List<StorageButton> storage) {
        this.storage = storage;
    }

    // Gets the current assignment
    public Assignment getCurrentAssignment() {
        if (Objects.isNull(nextAssignment)) {
            if (!iterator.hasNext()) {    // Start List anew
                iterator = assignments.iterator();
            }
            nextAssignment = iterator.next();
        }
        return nextAssignment;
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
        nextAssignment = null;
    }

    // If an warehousing assignment is selected store product in the storage space, if applicable
    // If an delivering assignment is selected deliver the product
    // If no assignment is selected then the storage space is selected for restorage
    public void manageStorage(StorageButton button) {
        message.setText("");
        // Currently no assignment is selected, the storage space has a product and another storage space is selected
        // -> it's time to restorage OR unselect if the same button is clicked twice!
        if (Objects.isNull(selectedAssignment) && Objects.nonNull(selectedStorage) && Objects.nonNull(selectedStorage.getProduct())) {
            if (button.equals(selectedStorage)) {
                selectOrUnselectButton(button, false);
                selectedStorage = null;
            } else {
                restorage(button);
            }
        }
        // No assignment, storage space nonNull and no selected storage
        // -> Select storage space
        else if (Objects.isNull(selectedAssignment) && Objects.nonNull(button.getProduct()) && Objects.isNull(selectedStorage)) {
            selectOrUnselectButton(button, true);
            selectedStorage = button;
        }
        else if (Objects.nonNull(selectedAssignment)) {
            if (Objects.nonNull(selectedStorage)) {
                // Reset possible selected storage
                System.out.println("Unselect was called");
                selectOrUnselectButton(selectedStorage, false);
                selectedStorage = null;
            }
            AssignmentButtonPanel abp = assignmentButtonPanels.stream().filter(assignmentButtonPanel -> assignmentButtonPanel.getAssignment() == selectedAssignment).findFirst().orElseThrow(NullPointerException::new);
            if (selectedAssignment.getType().equals(Assignment.Type.WAREHOUSING)) {                         // We want to store the product of the assignment
                if (commonStoreRulesPass(button) && selectedAssignment.getProduct().specificStoreRulesPass(storage.indexOf(button), storage, message)) {
                    button.setText("<html>" + selectedAssignment.getProduct().toFormattedString() + "</html>");
                    button.setProduct(selectedAssignment.getProduct());
                } else return;
            } else { // This is a delivering assignment
                int indexOfStorage = storage.indexOf(button);
                if (indexOfStorage > 9) {
                    message.setText("Nur Produkte auf der Vorderseite können ausgeliefert werden.");
                    return;
                }
                if (selectedAssignment.getProduct().getClass().equals(button.getProduct().getClass())) {    // Check if
                    if (selectedAssignment.getProduct().compareTo(button.getProduct())) {                   // Now for the detailed check
                        button.reset();
                    } else {
                        message.setText("Die Eigenschaften sind nicht wie gefordert.");
                        return;
                    }
                } else {
                    message.setText("Das ist nicht das gewollte Produkt.");
                    return;
                }
            }
            abp.reset();
            balance += selectedAssignment.getReward();
            balanceLabel.setText(String.valueOf(balance));
            selectedAssignment = null;
            // TODO: Add balance record
        }
    }

    private boolean commonStoreRulesPass(StorageButton button) {
        if (Objects.nonNull(button.getProduct())) {     // Storage space is already occupied
            message.setText("Platz bereits belegt. Umlagerung nötig.");
            return false;
        } else if (storage.indexOf(button) > 9) {                // You can not directly store into the back
            message.setText("Du kannst nichts direkt in den hinteren Lagerteil lagern.");
            return false;
        } else {
            return true;
        }
    }

    // Restorage a product
    private void restorage(StorageButton button) {
        if (commonRestorageRulesPass(button) && selectedStorage.getProduct().specificStoreRulesPass(storage.indexOf(button), storage, message)) {
            if (selectedStorage.isLongProduct()) {      // Special case: Also move back storage.
                StorageButton newBackStorage = storage.get(storage.indexOf(button) + 10);
                StorageButton oldBackStorage = storage.get(storage.indexOf(selectedStorage) + 10);
                newBackStorage.setText(oldBackStorage.getText());
                newBackStorage.setProduct(oldBackStorage.getProduct());
                newBackStorage.setLongProduct(oldBackStorage.isLongProduct());
                oldBackStorage.reset();
            }
            button.setText(selectedStorage.getText());
            button.setProduct(selectedStorage.getProduct());
            button.setLongProduct(selectedStorage.isLongProduct());
            selectedStorage.reset();
            selectOrUnselectButton(selectedStorage, false);
            selectedStorage = null;

            balance -= 50;
            balanceLabel.setText(String.valueOf(balance));
            // TODO: Add balance record
        }
    }

    private boolean commonRestorageRulesPass(StorageButton button) {
        if (Objects.nonNull(button.getProduct())) {
            message.setText("Der Lagerplatz ist bereits belegt");
            return false;
        }

        int fromStorageIndex = storage.indexOf(selectedStorage);
        int toStorageIndex = storage.indexOf(button);
        int offset = fromStorageIndex - toStorageIndex;

        if (Math.abs(offset) == 2 || Math.abs(offset) == 10) {
            return true;
        } else if (fromStorageIndex % 2 == 0) {
            if (offset == -1) {
                return true;
            }
            } else {
                if (offset == 1) {
                    return true;
                }
            }
            message.setText("Nur auf angrenzende Lagerplätze kann umgelagert werden.");
            return false;
    }


    // Select one Assignment from the four. Unselect other if others are currently selected.
    // Unselect if already selected.
    // Do nothing if the panel doesn't hold an assignment.
    public void setSelectedAssignment(AssignmentButtonPanel selectedAbp) {
        if (selectedAbp.getAssignment() == selectedAssignment) {
            selectOrUnselectButton(selectedAbp.getAssignmentButton(), false);
            selectedAssignment = null;
        } else if (Objects.nonNull(selectedAbp.getAssignment())) {
            for (AssignmentButtonPanel abp : assignmentButtonPanels) {
                if (abp.equals(selectedAbp)) {
                    selectOrUnselectButton(abp.getAssignmentButton(), true);
                    selectedAssignment = abp.getAssignment();
                } else {
                    selectOrUnselectButton(abp.getAssignmentButton(), false);
                }
            }
        }
    }

    // Marks a button if selected
    // Resets a button if unselected
    public void selectOrUnselectButton(JButtonExtension button, boolean selected) {
        if (selected) {
            button.setBackground(Color.green);
        } else {
            button.resetDesign();
        }
    }

    // Trashes the selected product if allowed
    public void trashProduct() {
        if (Objects.nonNull(selectedStorage)) {
            int indexOfStorage = storage.indexOf(selectedStorage);
            if (indexOfStorage == 8 || indexOfStorage == 9) {
                selectedStorage.reset();
                balance -= 300;
                balanceLabel.setText(String.valueOf(balance));
                // TODO Balance
            } else {
                message.setText("Nur Produkte auf der untersten Etage können geschreddert werden.");
            }
        } else {
            message.setText("Wähle zuerst das Produkt aus, das zu schreddern ist.");
        }
    }

    public void rejectAssignment(AssignmentButtonPanel assignmentButtonPanel) {
        balance -= assignmentButtonPanel.getAssignment().getReward();
        balanceLabel.setText(String.valueOf(balance));
        assignmentButtonPanel.reset();
        // TODO Balance
    }

    public void openBalanceTable() {
        new BalanceDialog(balanceHandler).setVisible(true);
    }
}
