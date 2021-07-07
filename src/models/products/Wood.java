package models.products;

import customui.StorageButton;
import models.Product;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class Wood implements Product {

    private Type type;
    private Form form;

    public enum Type {
        PINE,
        OAK,
        BEECH
    }

    public enum Form {
        PLANK,
        TIMBER,
        LOG
    }

    public Wood(Type type, Form form) {
        this.type = type;
        this.form = form;
    }

    @Override
    public boolean specificStoreRulesPass(int indexOfStorage, List<StorageButton> storage, JLabel message) {
        if (form.equals(Form.TIMBER)) {
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
            case TIMBER -> "Balken";
            case LOG -> "Scheit";
        };
    }

    @Override
    public String toFormattedString() {
        return "Produkt: Holz <br>"
                + "Art: " + getAttribute1() + "<br>"
                + "Form: " + getAttribute2();
    }

}
