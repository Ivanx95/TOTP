package com.example.totp;

import com.example.totp.security.TOTPAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Value(("${server.trusted-network:0.0.0.0}"))
    private String trustedNetwork;

    @Bean
    public AuthenticationManager totpAuthenticationManager() {
        TOTPAuthenticationProvider authenticationProvider = new TOTPAuthenticationProvider();

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/h2-console/**")
                                .access(hasIpAdress(trustedNetwork)))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/login").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/").permitAll())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .build();
    }


    public AuthorizationManager<RequestAuthorizationContext> hasIpAdress(String ipAddress){
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ipAddress);
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            return new AuthorizationDecision(ipAddressMatcher.matches(request));
        };
    }
}
