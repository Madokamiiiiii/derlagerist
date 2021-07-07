package utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

// This class handles everything that has to do with the balance
// Like the balance shown in the MainScreen or the balance table
public class BalanceHandler {
    private int revenue;
    private int costs;
    private int balance;

    private String[] columns = {
            "Nr.",
            "Art",
            "Betrag"
    };

    enum Type {
        WAREHOUSING,
        DELIVERING,
        RESTORAGE,
        TRASH,
        REJECTION
    }

    class Booking {
        int nr;
        Type type;
        int amount;

        public Booking(int nr, Type type, int amount) {
            this.nr = nr;
            this.type = type;
            this.amount = amount;
        }
    }

    public String[] getColumns() {
        return columns;
    }

    public Object[][] getBookingsVector() {
        List<Booking> bookings = new ArrayList<>();
        Booking test = new Booking(1, Type.REJECTION, 500);
        Booking test2 = new Booking(1, Type.REJECTION, 500);

        bookings.add(test);
        bookings.add(test2);

        Object[][] resu = new Object[2][3];
        for (int i = 0; i < 2; i++) {
            resu[i][0] = bookings.get(i).amount;
            resu[i][1] = bookings.get(i).type;

        }

        return resu;
    }


}
