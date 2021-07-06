import java.io.IOException;
import java.util.Arrays;

public class Start {

    public static void main(String[] args) throws IOException {
        try {
            new MainScreen().setVisible(true);
        } catch (Exception e) {
            throw e;
        }
    }

}
