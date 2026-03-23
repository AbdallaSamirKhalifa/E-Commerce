package commerce.service.implementation;

import commerce.service.contract.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class GmailService implements EmailService {

    private final JavaMailSender mailSender;
    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,"UTF-8");
        helper.setPriority(3);
        helper.setFrom("no-reply.gmail.com");
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(text);
        mailSender.send(message);
    }
}
