package br.com.farmacos.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("remedios_api")
						.version("V1.0")
						.description("Api para controle de farmacos e contigente de farmácias hospitalares")
						.termsOfService("https://github.com/MateusSouzaAlves/rest-spring-boot-controle-farmacos")
						.license(new License()
								.name("Apache 2.0")
								.url("https://github.com/MateusSouzaAlves/rest-spring-boot-controle-farmacos")
								)
						);
	}

}
