package com.tokioschool.flightapp.store.scurity.configuration.userdetails;

import com.tokioschool.flightapp.store.scurity.configuration.userdetails.properties.StoreUserConfigurationProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StoreUserDetailsInMemoryServiceConfiguration {

    private final StoreUserConfigurationProperty storeUserConfigurationProperty;

    @Bean
    public UserDetailsService userDetailsService() {
        // convierte el usuario que hay en memoria a un user detials de spring
        List<UserDetails> users = storeUserConfigurationProperty.users().stream()
                .map(user ->
                        User.builder()
                                .username( user.username() )
                                .password( user.password() )
                                .authorities( user.authorities().toArray(new String[0]) )
                                .roles( user.roles().toArray(new String[0]) )
                                .build()
                ).toList();

        return new InMemoryUserDetailsManager(users);
    }
}
