import Models.Assignment;

import javax.swing.*;
import java.awt.event.*;

public class NewAssignmentDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton buttonOK;
    private JLabel assignmentText;

    private final Assignment assignment;

    public NewAssignmentDialog(MainScreenController controller) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        assignment = controller.getAssignment();

        buttonOK.addActionListener(e -> onOK(controller));

        buttonCancel.addActionListener(e -> onCancel());

        assignmentText.setText(assignment.toFormattedText());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }

    private void onOK(MainScreenController controller) {
        controller.addNewAssignment(assignment);
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
