package Models;

public class Assignment {
    public enum Type {
        DELIVERING,
        WAREHOUSING
    }

    private Product product;
    private int reward;
    private Type type;

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
        return "<html>" + getTypeGerman() + "<br>"
                + product.toFormattedString()  + "<br>"
                + "Belohnung: "+  reward + "€</html>";
    }
}
