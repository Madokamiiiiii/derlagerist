package screens;

import customui.AssignmentButtonPanel;
import customui.JButtonExtension;
import customui.StorageButton;
import models.Assignment;
import utils.BalanceHandler;
import utils.CSVHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


// This class contains all the logic required for the MainScreen
public class MainScreenController {
    private final BalanceHandler balanceHandler;

    private final List<Assignment> assignments;
    private List<AssignmentButtonPanel> assignmentButtonPanels;
    private Iterator<Assignment> iterator;
    private Assignment nextAssignment;
    private AssignmentButtonPanel selectedAssignmentButtonPanel;
    private StorageButton selectedStorage;
    private JLabel message;
    private List<StorageButton> storage;

    public MainScreenController() {
        assignments = CSVHandler.getAssignmentsFromCSV();
        iterator = assignments.iterator();
        balanceHandler = new BalanceHandler();
    }

    public void setBalanceLabel(JLabel balanceLabel) {
        balanceHandler.setBalanceLabel(balanceLabel);
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
                    .setText("<html>" + assignment.toFormattedText() + "</html>");
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
        if (Objects.isNull(selectedAssignmentButtonPanel) && Objects.nonNull(selectedStorage) && Objects.nonNull(selectedStorage.getProduct())) {
            if (button.equals(selectedStorage)) {
                selectOrUnselectButton(button, false);
                selectedStorage = null;
            } else {
                restorage(button);
            }
        }
        // No assignment, storage space nonNull and no selected storage
        // -> Select storage space
        else if (Objects.isNull(selectedAssignmentButtonPanel) && Objects.nonNull(button.getProduct()) && Objects.isNull(selectedStorage)) {
            selectOrUnselectButton(button, true);
            selectedStorage = button;
        } else if (Objects.nonNull(selectedAssignmentButtonPanel)) {
            storeOrDeliverProduct(button);
        }
    }

    private void storeOrDeliverProduct(StorageButton button) {
        Assignment selectedAssignment = selectedAssignmentButtonPanel.getAssignment();
        if (Objects.nonNull(selectedStorage)) {
            // Reset possible selected storage
            selectOrUnselectButton(selectedStorage, false);
            selectedStorage = null;
        }
        // We want to store the product of the assignment
        if (selectedAssignment.getType().equals(Assignment.Type.WAREHOUSING)) {
            if (commonStoreRulesPass(button) && selectedAssignment.getProduct().specificStoreRulesPass(storage.indexOf(button), storage, message)) {
                button.setText(selectedAssignment.getProduct().toFormattedStringShort());
                button.setProduct(selectedAssignment.getProduct());
                try {
                    button.setIcon(selectedAssignment.getProduct().getIcon());
                } catch (IOException e) {
                    System.err.println("Icon not found");
                }
                balanceHandler.addBooking(selectedAssignment.getReward(), BalanceHandler.Type.WAREHOUSING);
            } else return;
        } else { // This is a delivering assignment
            int indexOfStorage = storage.indexOf(button);
            if (indexOfStorage > 9) {
                message.setText("Nur Produkte auf der Vorderseite können ausgeliefert werden.");
                return;
            }
            if (selectedAssignment.getProduct().getClass().equals(button.getProduct().getClass())) {    // Check if it's the same product class
                if (selectedAssignment.getProduct().compareTo(button.getProduct())) {                   // Now for the detailed check
                    button.reset();
                    balanceHandler.addBooking(selectedAssignment.getReward(), BalanceHandler.Type.DELIVERING);
                } else {
                    message.setText("Die Eigenschaften sind nicht wie gefordert.");
                    return;
                }
            } else {
                message.setText("Das ist nicht das gewollte Produkt.");
                return;
            }
        }

        selectedAssignmentButtonPanel.reset();
        selectedAssignmentButtonPanel = null;
    }

    private boolean commonStoreRulesPass(StorageButton button) {
        if (Objects.nonNull(button.getProduct())) {              // Storage space is already occupied
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
            if (selectedStorage.isLongProduct()) {              // Special case: Also move back storage.
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
            button.setIcon(selectedStorage.getIcon());
            selectedStorage.reset();
            selectOrUnselectButton(selectedStorage, false);
            selectedStorage = null;

            balanceHandler.addBooking(50, BalanceHandler.Type.RESTORAGE);
        }
    }

    private boolean commonRestorageRulesPass(StorageButton button) {
        if (Objects.nonNull(button.getProduct())) {
            message.setText("Der Lagerplatz ist bereits belegt.");
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
        if (selectedAbp == selectedAssignmentButtonPanel) {
            selectOrUnselectButton(selectedAbp.getAssignmentButton(), false);
            selectedAssignmentButtonPanel = null;
        } else if (Objects.nonNull(selectedAbp.getAssignment())) {
            for (AssignmentButtonPanel abp : assignmentButtonPanels) {
                if (abp.equals(selectedAbp)) {
                    selectOrUnselectButton(abp.getAssignmentButton(), true);
                    selectedAssignmentButtonPanel = abp;
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
                balanceHandler.addBooking(300, BalanceHandler.Type.TRASH);
                selectedStorage = null;
            } else {
                message.setText("Nur Produkte auf der untersten Etage können geschreddert werden.");
            }
        } else {
            message.setText("Wähle zuerst das Produkt aus, das zu schreddern ist.");
        }
    }

    public void rejectAssignment(AssignmentButtonPanel assignmentButtonPanel) {
        balanceHandler.addBooking(assignmentButtonPanel.getAssignment().getReward(), BalanceHandler.Type.REJECTION);
        assignmentButtonPanel.reset();
    }

    public void openBalanceTable() {
        new BalanceDialog(balanceHandler).setVisible(true);
    }
}
