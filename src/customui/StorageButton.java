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
        this.setPreferredSize(new Dimension(180, 90));
        this.setMinimumSize(new Dimension(140, 75));
        this.setMargin(new Insets(10, 5, 10, 5));
        this.setIconTextGap(3);
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
        this.setIcon(null);
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
