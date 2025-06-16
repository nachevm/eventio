package com.sportygroup.eventio.score.rest;

import com.sportygroup.eventio.score.api.ScoreData;
import com.sportygroup.eventio.score.api.ScoreService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implementation of {@link ScoreService} via REST API calls
 */
@Slf4j
@RequiredArgsConstructor
class ScoreRestService implements ScoreService {

    private final WebClient webClient;

    @Retryable
    @Override
    public ScoreData findById(@NonNull String id) {
        log.debug("Fetching score for id: {}", id);
        return webClient.get().uri("/" + id).retrieve().bodyToMono(ScoreData.class).block();
    }
}
