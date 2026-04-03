package commerce.service.util;

import commerce.entities.Customer;
import commerce.service.contract.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class OrderHelper {
    private final ContextCustomer contextCustomer;
    private final EmailService emailService;
    public Customer getContextCustomerWithCart(){
      return contextCustomer.getContextCustomerWithCartInfo();
    }
    public void sendOrderConfirmationEmail(String to, BigDecimal total, String customerName){
        emailService.sendEmailAsync(to,"Order Confirmation", String.
                format("Hello %s,\nYour order has been placed successfully with total of %.2f EGP.\nYou can visit our website again to continue shopping."
                        ,customerName,total ));
    }
}
