package com.sportygroup.eventio.sportevent.domain;

import com.sportygroup.eventio.messaging.api.MessagePublisher;
import com.sportygroup.eventio.score.api.ScoreData;
import com.sportygroup.eventio.score.api.ScoreService;
import com.sportygroup.eventio.sportevent.api.SportEventService;
import com.sportygroup.eventio.sportevent.api.SportEventStatus;
import com.sportygroup.eventio.sportevent.message.SportEventScoreMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class SportEventDomainService implements SportEventService {

  private final SportEventRepository sportEventRepository;
  private final MessagePublisher messagePublisher;
  private final ScoreService scoreService;
  private final SportEventProperties sportEventProperties;

  @Override
  public void updateStatus(@NonNull String eventId, @NonNull SportEventStatus status) {
    if (SportEventStatus.NOT_LIVE.equals(status)) {
      log.info("Removing live event {} if it exists", eventId);
      sportEventRepository.removeIfExists(eventId);
      return;
    }
    if (sportEventRepository.exists(eventId)) {
      log.info("Scheduled task for live event {} already exists", eventId);
      return;
    }
    log.info("Saving live event {}", eventId);
    sportEventRepository.save(eventId, () -> getAndPublishScore(eventId));
  }

  private void getAndPublishScore(@NonNull String eventId) {
    try {
      ScoreData score = scoreService.findById(eventId);
      messagePublisher.publish(sportEventProperties.getScoreMessagingKey(), new SportEventScoreMessage(eventId, score.currentScore()));
      log.debug("Published score for sport event {} with score {}", eventId, score.currentScore());
    } catch (Exception e) {
      log.error("Failed to publish score for sport event {}", eventId, e);
    }
  }
}
