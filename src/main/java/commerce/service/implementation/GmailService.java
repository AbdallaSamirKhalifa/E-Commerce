package commerce.service.implementation;

import commerce.service.contract.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GmailService implements EmailService {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(100, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setName("Email");
        return thread;
    });
    private final JavaMailSender mailSender;

    @Override
    public void sendEmailAsync(String to, String subject, String text)  {
        CompletableFuture.runAsync(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
                helper.setPriority(3);
                helper.setFrom("no-reply.gmail.com");
                helper.setSubject(subject);
                helper.setTo(to);
                helper.setText(text);
                mailSender.send(message);
            } catch (MessagingException e) {

            }

        }, EXECUTOR_SERVICE).exceptionally(ex -> {
            log.error("Error occurred while sending message to {}", to);
            log.trace("Stack: {}", (Object) ex.getStackTrace());
            return null;
        });

        log.info("Sending an Email to {}, With Subject {}", to, subject);
    }
}
