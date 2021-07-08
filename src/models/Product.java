package models;

import customui.StorageButton;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public interface Product {

    // Checks if the selected (re)storage passes rules specific to the product.
    boolean specificStoreRulesPass(int indexOfStorage, List<StorageButton> storage, JLabel message);

    String getAttribute1();

    String getAttribute2();

    // Returns formatted string for the assignment button.
    String toFormattedString();

    // Returns a shorter formatted string in html for the storage.
    String toFormattedStringShort();

    // Gets an icon for the buttons
    Icon getIcon() throws IOException;

    // Checks if two products are the same.
    default boolean compareTo(Product product) {
        return product.getAttribute1().equals(getAttribute1())
                && product.getAttribute2().equals(getAttribute2());
    }

}
