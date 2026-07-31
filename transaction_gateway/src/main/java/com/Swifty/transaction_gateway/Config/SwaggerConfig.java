package com.Swifty.transaction_gateway.Config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI transactionGatewayOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("SwiftPay Transaction Gateway API")

                        .description("REST APIs for Transaction Gateway Service")

                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Charan Tej")
                                .email("charan@example.com"))

                        .license(new License()
                                .name("Apache 2.0")))

                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));
    }
}