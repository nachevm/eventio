package com.sportygroup.eventio.sportevent.domain;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sportygroup.eventio.messaging.api.MessagePublisher;
import com.sportygroup.eventio.score.api.ScoreService;
import com.sportygroup.eventio.sportevent.api.SportEventStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SportEventDomainServiceTest {

  @Mock
  private SportEventRepository sportEventRepository;
  @Mock
  private MessagePublisher messagePublisher;
  @Mock
  private ScoreService scoreService;
  @Mock
  private SportEventProperties sportEventProperties;
  @InjectMocks
  private SportEventDomainService sportEventDomainService;

  @Test
  void shouldRemoveLiveEventWhenStatusIsNotLive() {
    String eventId = "event123";

    sportEventDomainService.updateStatus(eventId, SportEventStatus.NOT_LIVE);

    verify(sportEventRepository, times(1)).removeIfExists(eventId);
    verify(sportEventRepository, never()).save(eq(eventId), any());
  }

  @Test
  void shouldDoNothingWhenEventAlreadyExistsAndStatusIsLive() {
    String eventId = "event123";
    when(sportEventRepository.exists(eventId)).thenReturn(true);

    sportEventDomainService.updateStatus(eventId, SportEventStatus.LIVE);

    verify(sportEventRepository, never()).removeIfExists(eventId);
    verify(sportEventRepository, never()).save(eq(eventId), any());
  }

  @Test
  void shouldSaveNewLiveEventSuccessfully() {
    String eventId = "event123";
    when(sportEventRepository.exists(eventId)).thenReturn(false);

    sportEventDomainService.updateStatus(eventId, SportEventStatus.LIVE);

    verify(sportEventRepository, never()).removeIfExists(eventId);
    verify(sportEventRepository, times(1)).save(eq(eventId), any());
  }
}
