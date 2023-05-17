package rainer.pawel.elevator.system.infrastructure.jackson;

import rainer.pawel.elevator.system.domain.Id;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer modulesSettingJackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializers(new IdSerializer(Id.class));
        };
    }
}
