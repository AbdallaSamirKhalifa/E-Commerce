package commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
@Bean
public CommandLineRunner commandLineRunner(RoleRepository repository) {
    return args -> {
        if (repository.findRoleByName("ROLE_CUSTOMER").isEmpty())
        {
            Role role=new Role();
            role.setName("ROLE_CUSTOMER");
            repository.save(role);
        }
    };
}
}
