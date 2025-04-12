package com.example.spring_jdbc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Danh sách các endpoint công khai không yêu cầu xác thực
    private static final String[] PUBLIC_ENDPOINTS = {
            "/users",
            "/authentication/token",
            "/authentication/introspect"
    };

    @Value("${jwt.signing.key}")
    private String signingKey; // Khóa bí mật để ký JWT

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        // Các endpoint công khai
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        // Tất cả các request khác yêu cầu xác thực và quyền Sopec_admin
                        .anyRequest().hasAuthority("Role_admin")
                );
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
//                )
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
                 httpSecurity.oauth2ResourceServer(oauth2 ->
                         oauth2.jwt(jwtConfigurer ->
                                 jwtConfigurer.decoder(jwtDecoder())
                         .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                 );
                 httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("Role_");
        JwtAuthenticationConverter jwtAuthenticationConverter=new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;

    }


    @Bean
    public JwtDecoder jwtDecoder() {
        // Tạo khóa bí mật từ signingKey với thuật toán HS512
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                signingKey.getBytes(StandardCharsets.UTF_8),
                "HS512");

        // Sử dụng NimbusJwtDecoder để giải mã JWT
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10); // Bộ mã hóa password với độ mạnh 10
    }
}