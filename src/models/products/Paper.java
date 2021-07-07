package models.products;

import customui.StorageButton;
import models.Product;

import javax.swing.*;
import java.util.List;

public class Paper implements Product {

    private Color color;
    private Size size;

    public enum Color {
        WHITE,
        GREEN,
        BLUE
    }

    public enum Size {
        A3,
        A4,
        A5
    }

    public Paper(Color color, Size size) {
        this.color = color;
        this.size = size;
    }

    @Override
    public boolean specificStoreRulesPass(int indexOfStorage, List<StorageButton> storage, JLabel message) {
        return true;
    }

    @Override
    public String getAttribute1() {
        return switch (color) {
            case WHITE -> "Weiß";
            case BLUE -> "Blau";
            case GREEN -> "Grün";
        };
    }

    @Override
    public String getAttribute2() {
        return size.name();
    }

    @Override
    public String toFormattedString() {
        return "Produkt: Papier <br>"
                + "Farbe: " + getAttribute1() + "<br>"
                + "Größe: " + getAttribute2();
    }
}
