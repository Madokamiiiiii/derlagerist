package Models;

public interface Product {

    boolean productSpecificTurnAllowed();

    String getAttribute1();
    String getAttribute2();

    String toFormattedString();

}
