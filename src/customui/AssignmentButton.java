package customui;

import java.awt.*;

public class AssignmentButton extends JButtonExtension {

    public AssignmentButton(String text) {
        super(text);
        this.setPreferredSize(new Dimension(150, 100));
        this.setMargin(new Insets(1, 1, 1, 1));

    }

    @Override
    public void resetDesign() {
        this.setBackground(null);
    }
}
