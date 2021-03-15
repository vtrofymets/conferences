package conferences;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ConferencesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferencesServiceApplication.class, args);
    }

}
