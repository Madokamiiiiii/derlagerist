package models.products;

import customui.StorageButton;
import models.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Paper implements Product {

    private final Color color;
    private final Size size;

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

    @Override
    public String toFormattedStringShort() {
        return "<html>Papier <br>"
                + getAttribute1() + "<br>"
                + getAttribute2() + "</html>";
    }

    @Override
    public Icon getIcon() throws IOException {
        String iconPath = "";
        switch (color) {
            case WHITE -> iconPath = "/assets/paper_white.png";
            case GREEN -> iconPath = "/assets/paper_green.png";
            case BLUE -> iconPath = "/assets/paper_blue.png";
        }

        return new ImageIcon(ImageIO.read(Objects.requireNonNull(this.getClass().getResource(iconPath))).getScaledInstance(88, 69, Image.SCALE_SMOOTH));
    }

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
}
