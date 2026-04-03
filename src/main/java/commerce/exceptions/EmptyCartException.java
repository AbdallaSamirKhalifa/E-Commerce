package commerce.exceptions;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException() {
        super("Your cart is empty, please add items to cart first");
    }
}
