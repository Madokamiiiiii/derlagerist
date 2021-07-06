package Models;

public class Assignment {
    enum Type {
        DELIVERING,
        WAREHOUSING
    }

    private Product product;
    private int reward;
    private int units;

    public Assignment(Product product, int reward, int units) {
        this.product = product;
        this.reward = reward;
        this.units = units;
    }

    public Product getProduct() {
        return product;
    }

    public int getReward() {
        return reward;
    }

    public int getUnits() {
        return units;
    }
}
