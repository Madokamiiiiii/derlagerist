package screens;

import utils.BalanceHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;

public class BalanceDialog extends JDialog {
    private JButton buttonOK;

    public BalanceDialog(BalanceHandler balanceHandler) {
        Container c = this.getContentPane();
        this.setModal(true);
        this.getRootPane().setDefaultButton(buttonOK);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600, 700);
        this.setTitle("Umsätze");
        this.setLayout(new BorderLayout());

        // Custom model to disable editing of cells
        DefaultTableModel tableModel = new DefaultTableModel(balanceHandler.getBookingsData(), balanceHandler.getColumns()) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable balanceTable = new JTable();
        balanceTable.setModel(tableModel);

        JScrollPane tablePane = new JScrollPane(balanceTable);
        tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(tablePane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JPanel sumsPanel = new JPanel();
        sumsPanel.setLayout(new BoxLayout(sumsPanel, BoxLayout.X_AXIS));
        JLabel revenueLabel = new JLabel("Gesamt-Umsatz: ");
        JLabel revenue = new JLabel(String.valueOf(balanceHandler.getRevenue()));
        sumsPanel.add(Box.createHorizontalStrut(10));
        sumsPanel.add(revenueLabel);
        sumsPanel.add(revenue);

        sumsPanel.add(Box.createHorizontalGlue());

        JLabel costLabel = new JLabel("Gesamt-Kosten: ");
        JLabel costs = new JLabel(String.valueOf(balanceHandler.getCosts()));
        sumsPanel.add(costLabel);
        sumsPanel.add(costs);
        sumsPanel.add(Box.createHorizontalStrut(10));

        bottomPanel.add(sumsPanel);

        buttonOK = new JButton("OK");
        buttonOK.addActionListener(e -> dispose());
        bottomPanel.add(buttonOK);
        bottomPanel.add(Box.createVerticalStrut(5));

        c.add(bottomPanel, BorderLayout.SOUTH);

        // Pressing ESCAPE closes the window
        tablePane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }
}
