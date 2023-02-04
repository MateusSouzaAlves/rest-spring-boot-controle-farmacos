package br.com.farmacos.config.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("remedios_api")
						.version("0.0.1")
						.description("Api para controle de farmacos e contigente de farmácias hospitalares.")
						.termsOfService("https://github.com/MateusSouzaAlves/rest-spring-boot-controle-farmacos")
						.contact(new Contact()
								.name("Suporte via email")
								.email("mateus_amoavida_2@hotmail.com"))
						.license(new License()
								.name("APACHE 2.0")
								.url("https://www.apache.org/licenses/LICENSE-2.0")
								)
						).components(new Components()
								.addSecuritySchemes("bearerAuth", new SecurityScheme().type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								)
						).security(Collections.singletonList(new SecurityRequirement().addList("bearerAuth")));
			}

}
