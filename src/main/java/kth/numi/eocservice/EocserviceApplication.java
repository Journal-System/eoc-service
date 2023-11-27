package kth.numi.eocservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;


@SpringBootApplication
@Controller
@OpenAPIDefinition(
		info = @Info(
				title = "Patient Journal",
				version = "1.0.3",
				description = "APIs for Patient Journal website",
				termsOfService = "none for now"
		)
)
public class EocserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EocserviceApplication.class, args);
	}

	@Bean
	public GroupedOpenApi customOpenAPI() {
		return GroupedOpenApi.builder()
				.group("Patient_Journal_APIs")
				.pathsToMatch(
						"/condition/**",
						"/observation/**",
						"/encounter/**")
				.build();
	}

}
