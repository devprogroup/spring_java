package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import java.util.List;

@Configuration
public class CustomAuthManagerConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public CustomAuthManagerConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() {
        return new ProviderManager(List.of(customAuthenticationProvider));
    }
}
