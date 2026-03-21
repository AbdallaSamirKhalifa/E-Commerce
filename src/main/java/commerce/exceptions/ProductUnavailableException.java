package commerce.exceptions;

import org.springframework.mail.MailException;

public class ProductUnavailableException extends RuntimeException{
    public ProductUnavailableException(String message)
    {
        super(message);
    }
}
