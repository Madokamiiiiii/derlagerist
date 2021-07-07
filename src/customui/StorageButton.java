package customui;

import models.Product;

import javax.swing.*;
import java.awt.*;

public class StorageButton extends JButtonExtension {
    private Product product;
    private boolean longProduct = false;

    public StorageButton(String text) {
        super(text);
        this.setBackground(Color.gray);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    @Override
    public void resetDesign() {
        this.setBackground(Color.gray);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    public void reset() {
        product = null;
        longProduct = false;
        this.setText("Leer");
        resetDesign();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isLongProduct() {
        return longProduct;
    }

    public void setLongProduct(boolean longProduct) {
        this.longProduct = longProduct;
    }
}
