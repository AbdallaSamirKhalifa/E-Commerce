package commerce.service.contract;

import jakarta.mail.MessagingException;

public interface EmailService {
    /**
     * Sends a MIME message with HTML content using Thymeleaf template engine.
     *
     * @param to      the recipient email address
     * @param subject the subject of the email
     * @param text    the body of the email
     * @throws MessagingException if an error occurs while sending the email
     */
    void sendEmail(String to, String subject, String text) throws MessagingException;
}
