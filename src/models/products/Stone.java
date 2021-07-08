package models.products;

import customui.StorageButton;
import models.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Stone implements Product {

    private final Type type;
    private final Weight weight;

    public Stone(Type type, Weight weight) {
        this.type = type;
        this.weight = weight;
    }

    @Override
    public boolean specificStoreRulesPass(int indexOfStorage, List<StorageButton> storage, JLabel message) {
        if (weight.equals(Weight.HEAVY)) {
            if ((indexOfStorage >= 6 && indexOfStorage <= 9)
                    || (indexOfStorage >= 16 && indexOfStorage <= 19)) {
                return true;
            } else {
                message.setText("Schwere Steine können nur auf den unteren beiden Plätzen gelagert werden.");
                return false;
            }
        } else if (weight.equals(Weight.MIDDLE)) {
            if ((indexOfStorage >= 2 && indexOfStorage <= 9)
                    || (indexOfStorage >= 12 && indexOfStorage <= 19)) {
                return true;
            } else {
                message.setText("Mittelschwere Steine können nicht ganz oben gelagert werden.");
                return false;
            }
        }
        return true;
    }

    @Override
    public String getAttribute1() {
        return switch (type) {
            case MARBLE -> "Marmor";
            case GRANITE -> "Granit";
            case SANDSTONE -> "Sandstein";
        };
    }

    @Override
    public String getAttribute2() {
        return switch (weight) {
            case HEAVY -> "Schwer";
            case MIDDLE -> "Mittel";
            case LIGHT -> "Leicht";
        };
    }

    @Override
    public String toFormattedString() {
        return "Produkt: Stein <br>"
                + "Art: " + getAttribute1() + "<br>"
                + "Gewicht: " + getAttribute2();
    }

    @Override
    public String toFormattedStringShort() {
        return "<html>Stein <br>"
                + getAttribute1() + "<br>"
                + getAttribute2() + "</html>";
    }

    @Override
    public Icon getIcon() throws IOException {
        return switch (weight) {
            case HEAVY -> new ImageIcon(ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/stone_heavy.png"))).getScaledInstance(90, 60, Image.SCALE_SMOOTH));
            case MIDDLE -> new ImageIcon(ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/stone_middle.png"))).getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            case LIGHT -> new ImageIcon(ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/stone_light.png"))).getScaledInstance(60, 45, Image.SCALE_SMOOTH));
        };
    }

    public enum Type {
        MARBLE,
        GRANITE,
        SANDSTONE
    }

    public enum Weight {
        LIGHT,
        MIDDLE,
        HEAVY
    }

}
