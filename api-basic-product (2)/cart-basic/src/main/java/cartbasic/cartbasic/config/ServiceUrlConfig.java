package cartbasic.cartbasic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "basic.services")
public record ServiceUrlConfig(
        String product ) {}