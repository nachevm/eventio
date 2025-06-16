package com.sportygroup.eventio.sportevent.domain;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SportEventRepositoryTest {

  @Mock
  private ScheduledExecutorService sportEventScheduledExecutorService;
  @Mock
  private Map<String, ScheduledFuture<?>> taskByLiveEventId;
  @InjectMocks
  private SportEventRepository sportEventRepository;

  @Test
  void removeIfExists() {
    ScheduledFuture future = Mockito.mock(ScheduledFuture.class);
    doReturn(future).when(taskByLiveEventId).get("1");

    sportEventRepository.removeIfExists("1");

    verify(taskByLiveEventId).get("1");
    verify(future).cancel(false);
    verify(taskByLiveEventId).remove("1");
  }

  @Test
  void save() {
    Runnable runnable = Mockito.mock(Runnable.class);

    sportEventRepository.save("1", runnable);

    verify(sportEventScheduledExecutorService).scheduleAtFixedRate(runnable, 0, 10, TimeUnit.SECONDS);
  }
}
