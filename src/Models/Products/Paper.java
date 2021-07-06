package Models.Products;

import Models.Product;

public class Paper implements Product {



    @Override
    public boolean productSpecificTurnAllowed() {
        return false;
    }
}
