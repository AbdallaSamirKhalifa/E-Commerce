package commerce.exceptions;

public class ProductUnavailableException extends RuntimeException {
    public ProductUnavailableException(String productName) {
        super(String.format("Product %s is not available at the moment. Please try again later", productName));
    }
}
