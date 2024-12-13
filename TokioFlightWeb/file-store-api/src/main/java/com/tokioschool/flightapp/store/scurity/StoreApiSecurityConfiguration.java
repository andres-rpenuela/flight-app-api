package com.tokioschool.flightapp.store.scurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class StoreApiSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChainMainly(HttpSecurity httpSecurity) throws Exception {
        // define una cadena de seugridad
        return httpSecurity
                // gestion de securizar los endpoints
                .securityMatcher("/store/api/**")
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.POST,"/store/api/resources/")
                        .hasAuthority("write-resource")
                        .requestMatchers(HttpMethod.DELETE,"/store/api/resources/**")
                        .hasAuthority("write-resource")
                        .requestMatchers(HttpMethod.GET,"/store/api/resources/**")
                        .hasAnyAuthority("write-resource", "read-resource")
                        .requestMatchers("/store/api/**")
                        .authenticated()
                )
                // Gestion de session sin estado
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.
                                sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // se desabilita el csrf, al no generar formualiros
                .csrf(AbstractHttpConfigurer::disable)
                // corfs por defecto para recibir petionces del localhost
                .cors(Customizer.withDefaults())
         .build();
    }
}
