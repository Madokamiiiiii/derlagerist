package Models.Products;

import Models.Product;

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
    public boolean productSpecificTurnAllowed() {
        return false;
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

    // Returns formatted string for use for the storage etc
    @Override
    public String toFormattedString() {
        return "Produkt: Stein <br>"
                + "Art: " + getAttribute1() + "<br>"
                + "Gewicht: " + getAttribute2();
    }
}
