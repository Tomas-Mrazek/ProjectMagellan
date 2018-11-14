package cz.jaktoviditoka.projectmagellan.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import cz.jaktoviditoka.projectmagellan.settings.AppSettings;
import cz.jaktoviditoka.projectmagellan.ssdp.SSDPClient;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public AppSettings appSettings() {
        return new AppSettings();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public SSDPClient ssdpClient() {
        return new SSDPClient();
    }

}
