package customui;

import javax.swing.*;

// This class adds € to the amount of money
public class MoneyLabel extends JLabel {

    public MoneyLabel(String text) {
        super(text);
    }

    @Override
    public void setText(String text) {
        super.setText(text + "€");
    }
}
