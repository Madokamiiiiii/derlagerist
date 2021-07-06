package Models.Products;

import Models.Product;

public class Wood implements Product {

    private Type type;
    private Form form;

    enum Type {
        PINE,
        OAK,
        BEECH
    }

    enum Form {
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
            case OAK -> "Buche";
            case BEECH -> "Eiche";
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

}
