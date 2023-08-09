package cartbasic.cartbasic;

import cartbasic.cartbasic.config.ServiceUrlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ServiceUrlConfig.class)
public class CartBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartBasicApplication.class, args);
	}

}
