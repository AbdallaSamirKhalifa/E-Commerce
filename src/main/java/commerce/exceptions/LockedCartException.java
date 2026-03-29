package commerce.exceptions;

public class LockedCartException extends RuntimeException {
    public LockedCartException() {
        super("Your cart is locked, means that you are running a checkout process.\nPlease wait until process is completed.");
    }
}
