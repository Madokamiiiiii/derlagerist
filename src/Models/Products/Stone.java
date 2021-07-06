package Models.Products;

import Models.Product;

public class Stone implements Product {

    private Type type;
    private Weight weight;

    enum Type {
        MARBLE,
        GRANITE,
        SANDSTONE
    }

    enum Weight {
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
        return null;
    }

    @Override
    public String getAttribute2() {
        return null;
    }
}
