package models.products;

import customui.StorageButton;
import models.Product;

import javax.swing.*;
import java.util.List;

public class Stone implements Product {

    private Type type;
    private Weight weight;

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

    public Stone(Type type, Weight weight) {
        this.type = type;
        this.weight = weight;
    }

    @Override
    public boolean specificStoreRulesPass(int indexOfStorage, List<StorageButton> storage, JLabel message) {
        if (weight.equals(Weight.HEAVY)) {
            if ((indexOfStorage >= 6 && indexOfStorage <= 9)
                    || ((indexOfStorage >= 16 && indexOfStorage <= 19))) {
                return true;
            } else {
                message.setText("Schwere Steine können nur auf den unteren beiden Plätzen gelagert werden.");
                return false;
            }
        } else if (weight.equals(Weight.MIDDLE)) {
            if ((indexOfStorage >= 2 && indexOfStorage <= 9)
                    || ((indexOfStorage >= 12 && indexOfStorage <= 19))) {
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
            case MIDDLE -> "Mittelschwer";
            case LIGHT -> "Leicht";
        };
    }

    @Override
    public String toFormattedString() {
        return "Produkt: Stein <br>"
                + "Art: " + getAttribute1() + "<br>"
                + "Gewicht: " + getAttribute2();
    }

}
