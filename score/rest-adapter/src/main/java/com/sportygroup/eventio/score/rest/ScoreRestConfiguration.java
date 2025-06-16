package com.sportygroup.eventio.score.rest;

import com.sportygroup.eventio.score.api.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableRetry
@EnableConfigurationProperties(ScoreRestProperties.class)
@Profile("score-rest")
@PropertySource("classpath:/score-rest.properties")
@RequiredArgsConstructor
class ScoreRestConfiguration {

    private final ScoreRestProperties properties;

    @Bean
    WebClient scoreRestWebClient(WebClient.Builder builder) {
        return builder.baseUrl(properties.getUrl()).build();
    }

    @Bean
    ScoreService scoreRestService(WebClient scoreRestWebClient) {
        return new ScoreRestService(scoreRestWebClient);
    }
}
