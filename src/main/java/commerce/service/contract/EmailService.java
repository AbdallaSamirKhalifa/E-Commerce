package commerce.service.contract;


public interface EmailService {
    /**
     * Sends a MIME message with HTML content using Thymeleaf template engine.
     *
     * @param to      the recipient email address
     * @param subject the subject of the email
     * @param text    the body of the email
     */
    void sendEmailAsync(String to, String subject, String text) ;
}
