package de.dhbw.shipwatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class DigitrafficApiConfig {

    @Value("${digitraffic.api.base-url}")
    private String baseUrl;

    @Value("${digitraffic.api.user}")
    private String user;

    @Value("${digitraffic.api.connect-timeout-ms}")
    private int connectTimeoutMs;

    @Value("${digitraffic.api.read-timeout-ms}")
    private int readTimeoutMs;

    @Bean
    public RestClient digitrafficRestClient() {
        // Apache HttpClient statt SimpleClient: schickt Accept-Encoding: gzip, das verlangt Digitraffic
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(connectTimeoutMs));
        factory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

        return RestClient.builder()
            .baseUrl(baseUrl)
            .requestFactory(factory)
            .defaultHeader("Accept", "application/json")
            // Digitraffic braucht keinen API-Key, bittet aber um diesen Header
            .defaultHeader("Digitraffic-User", user)
            .build();
    }
}
