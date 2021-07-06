package Models.Products;

import Models.Product;

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
        BAR,
        LOG
    }

    public Wood(Type type, Form form) {
        this.type = type;
        this.form = form;
    }

    @Override
    public boolean productSpecificTurnAllowed() {
        return false;
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
            case BAR -> "Balken";
            case LOG -> "Scheit";
        };
    }

    // Returns formatted string for use for the storage etc
    @Override
    public String toFormattedString() {
        return "Produkt: Papier <br>"
                + "Art: " + getAttribute1() + "<br>"
                + "Form: " + getAttribute2();
    }
}
