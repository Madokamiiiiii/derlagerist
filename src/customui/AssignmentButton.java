package customui;

public class AssignmentButton extends JButtonExtension {

    public AssignmentButton(String text) {
        super(text);
    }

    @Override
    public void resetDesign() {
        this.setBackground(null);
    }
}
