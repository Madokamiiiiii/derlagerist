package screens;

import utils.BalanceHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BalanceDialog extends JDialog {
    private JButton buttonOK;
    private JTable balanceTable;

    public BalanceDialog(BalanceHandler balanceHandler) {
        Container c = this.getContentPane();
        this.setModal(true);
        this.getRootPane().setDefaultButton(buttonOK);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setSize(600, 900);
        this.setTitle("Umsätze");
        this.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(balanceHandler.getBookingsVector(), balanceHandler.getColumns()) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        balanceTable = new JTable();
        balanceTable.setModel(tableModel);

        JScrollPane tablePane = new JScrollPane(balanceTable);
        tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(tablePane, BorderLayout.CENTER);

        buttonOK = new JButton("OK");
        buttonOK.addActionListener(e -> dispose());
        c.add(buttonOK, BorderLayout.SOUTH);


    }
}
