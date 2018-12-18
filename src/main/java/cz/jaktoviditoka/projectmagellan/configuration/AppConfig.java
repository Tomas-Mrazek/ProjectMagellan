package cz.jaktoviditoka.projectmagellan.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jaktoviditoka.projectmagellan.utils.PerformanceClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebClient webclient() {
        return WebClient.create();
    }

    @Bean
    public PerformanceClock performanceClock() {
        return new PerformanceClock();
    }

}
