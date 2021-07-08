package utils;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// This class handles everything that has to do with the balance
// Like the balance shown in the MainScreen or the balance table
public class BalanceHandler {
    private static final String[] columns = {
            "Nr.",
            "Art",
            "Betrag",
            "Kontostand"
    };
    private final List<Booking> bookings = new ArrayList<>();
    private JLabel balanceLabel;
    private int nr = 1;
    private int revenue = 0;
    private int costs = 0;
    private int balance;

    public String[] getColumns() {
        return columns;
    }

    public int getCosts() {
        return costs;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setBalanceLabel(JLabel balanceLabel) {
        this.balanceLabel = balanceLabel;
    }

    public String[][] getBookingsData() {

        String[][] bookingsArray = new String[bookings.size()][4];
        for (int i = 0; i < bookings.size(); i++) {
            bookingsArray[i][0] = bookings.get(i).nr;
            bookingsArray[i][1] = bookings.get(i).typeToString();
            bookingsArray[i][2] = bookings.get(i).amount;
            bookingsArray[i][3] = bookings.get(i).balance;
        }

        return bookingsArray;
    }

    public void addBooking(int amount, Type type) {
        switch (type) {
            case WAREHOUSING, DELIVERING -> revenue += amount;
            case REJECTION, TRASH, RESTORAGE -> {
                costs += amount;
                amount = -amount;
            }
        }

        balance += amount;
        balanceLabel.setText(String.valueOf(balance));
        if (balance < 0) {
            balanceLabel.setForeground(Color.RED);
        } else {
            balanceLabel.setForeground(Color.BLACK);
        }

        bookings.add(new Booking(
                nr,
                type,
                amount,
                balance
        ));

        nr++;
    }

    public enum Type {
        WAREHOUSING,
        DELIVERING,
        RESTORAGE,
        TRASH,
        REJECTION
    }

    static class Booking {
        public final String nr;
        public final Type type;
        public final String amount;
        public final String balance;

        public Booking(int nr, Type type, int amount, int balance) {
            this.nr = String.valueOf(nr);
            this.type = type;
            this.amount = amount + "€";
            this.balance = balance + "€";
        }

        public String typeToString() {
            return switch (type) {
                case WAREHOUSING -> "Einlagerung";
                case DELIVERING -> "Auslieferung";
                case RESTORAGE -> "Umlagerung";
                case TRASH -> "Schreddern";
                case REJECTION -> "Auftragsablehnung";
            };
        }
    }

}
