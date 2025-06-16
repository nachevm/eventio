package com.sportygroup.eventio.score.inprocess;

import com.sportygroup.eventio.score.api.ScoreData;
import com.sportygroup.eventio.score.api.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link ScoreService} that just returns mock data currently.
 */
@Slf4j
@Profile("score-in-process")
@Component
class InProcessScoreService implements ScoreService {

    @Override
    public ScoreData findById(String id) {
        return new ScoreData(id, "0:0");
    }
}
