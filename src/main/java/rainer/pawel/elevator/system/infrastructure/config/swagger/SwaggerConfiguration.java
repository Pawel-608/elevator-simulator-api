package rainer.pawel.elevator.system.infrastructure.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;

@Configuration
@Controller
@OpenAPIDefinition(
        info = @Info(
                title = "Elevator Control System"
        )
)
public class SwaggerConfiguration {

    private static final String BASE_PACKAGE = "rainer.pawel.elevator.system";

    static {
        SpringDocUtils.getConfig()
                .replaceWithClass(Id.class, String.class)
                .replaceWithClass(Floor.class, Integer.class);
    }

    @Bean
    GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Elevator API")
                .packagesToScan(BASE_PACKAGE)
                .build();
    }

    @GetMapping("/")
    String swaggerUi() {
        return "redirect:/swagger-ui/index.html";
    }

}
