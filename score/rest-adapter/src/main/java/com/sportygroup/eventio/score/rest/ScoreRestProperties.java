package com.sportygroup.eventio.score.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("score-rest")
final class ScoreRestProperties {

    private String url;
}
