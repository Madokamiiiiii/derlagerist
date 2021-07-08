package models.products;

import customui.StorageButton;
import models.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Wood implements Product {

    private final Type type;
    private final Form form;

    public Wood(Type type, Form form) {
        this.type = type;
        this.form = form;
    }

    @Override
    public boolean specificStoreRulesPass(int indexOfStorage, List<StorageButton> storage, JLabel message) {
        if (form.equals(Form.BEAM)) {
            if (indexOfStorage > 9) {
                message.setText("Holzbalken können aufgrund ihrer Größe nur vorne abgelegt werden.");
                return false;
            }
            StorageButton backStorage = storage.get(indexOfStorage + 10);
            if (Objects.isNull(backStorage.getProduct())) {
                backStorage.setText("Belegt durch Holzbalken.");
                backStorage.setProduct(storage.get(indexOfStorage).getProduct());
                storage.get(indexOfStorage).setLongProduct(true);
            } else {
                message.setText("Ein Holzbalken benötigt auch die hintere Palette.");
                return false;
            }
        }
        return true;

    }

    @Override
    public String getAttribute1() {
        return switch (type) {
            case PINE -> "Kiefer";
            case OAK -> "Eiche";
            case BEECH -> "Buche";
        };
    }

    @Override
    public String getAttribute2() {
        return switch (form) {
            case PLANK -> "Brett";
            case BEAM -> "Balken";
            case LOG -> "Scheit";
        };
    }

    @Override
    public String toFormattedString() {
        return "Produkt: Holz <br>"
                + "Art: " + getAttribute1() + "<br>"
                + "Form: " + getAttribute2();
    }

    @Override
    public String toFormattedStringShort() {
        return "<html>Holz <br>"
                + getAttribute1() + "<br>"
                + getAttribute2() + "</html>";
    }

    @Override
    public Icon getIcon() throws IOException {
        return switch (form) {
            case PLANK -> new ImageIcon(ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/wood_plank.png"))).getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            case BEAM -> new ImageIcon(ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/wood_beam.png"))).getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            case LOG -> new ImageIcon(ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/wood_log.png"))).getScaledInstance(60, 40, Image.SCALE_SMOOTH));
        };
    }

    public enum Type {
        PINE,
        OAK,
        BEECH
    }

    public enum Form {
        PLANK,
        BEAM,
        LOG
    }

}
