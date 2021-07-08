import screens.MainScreen;

import javax.swing.*;
import java.io.IOException;

public class Start {

    public static void main(String[] args) throws IOException {
        // Fixes not localized dialog text
        UIManager.put("OptionPane.noButtonText", "Nein");
        UIManager.put("OptionPane.yesButtonText", "Ja");

        new MainScreen().setVisible(true);
    }

}
