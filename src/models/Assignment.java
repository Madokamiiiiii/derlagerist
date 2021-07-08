package models;

public class Assignment {
    private final Product product;
    private final int reward;
    private final Type type;

    public Assignment(Product product, int reward, Type type) {
        this.product = product;
        this.reward = reward;
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public int getReward() {
        return reward;
    }

    public Type getType() {
        return type;
    }

    public String getTypeGerman() {
        return switch (type) {
            case DELIVERING -> "Auslagerung";
            case WAREHOUSING -> "Einlagerung";
        };
    }

    // Method which returns the assignment in html format with reward and type of assignment
    public String toFormattedText() {
        return getTypeGerman() + "<br>"
                + product.toFormattedString() + "<br>"
                + "Belohnung: " + reward + "€";
    }

    public enum Type {
        DELIVERING,
        WAREHOUSING
    }
}
