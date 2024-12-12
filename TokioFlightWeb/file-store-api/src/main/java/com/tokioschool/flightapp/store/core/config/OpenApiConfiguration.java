package com.tokioschool.flightapp.store.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =  @Info(
                title = "File Store API",
                version = "1.0",
                description = "Store service to manage file resources"
        )
)
public class OpenApiConfiguration {
}
