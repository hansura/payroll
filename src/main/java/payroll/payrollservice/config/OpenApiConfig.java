package payroll.payrollservice.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("http",new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))

                .info(new Info().title("Enat Remedy API").description(
                        "Payroll Management Service")
                        .version("0.0.1-SNAPSHOT")
                .contact(new Contact().email("isd@enatbanksc.com").name("ISD Department")));
    }
}