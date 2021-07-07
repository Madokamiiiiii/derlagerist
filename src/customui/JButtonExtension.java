package customui;

import javax.swing.*;

public abstract class JButtonExtension extends JButton {

    public JButtonExtension(String text) {
        super(text);
    }

    public void resetDesign() {};
}
