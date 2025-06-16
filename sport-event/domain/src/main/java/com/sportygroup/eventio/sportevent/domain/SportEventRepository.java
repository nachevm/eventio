package com.sportygroup.eventio.sportevent.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

@Repository
@RequiredArgsConstructor
class SportEventRepository {

    private final Map<String, ScheduledFuture<?>> taskByLiveEventId;
    private final ScheduledExecutorService sportEventScheduledExecutorService;

    void removeIfExists(String eventId) {
        Optional.ofNullable(taskByLiveEventId.get(eventId)).ifPresent(future -> future.cancel(false));
        taskByLiveEventId.remove(eventId);
    }

    boolean exists(String eventId) {
        return taskByLiveEventId.containsKey(eventId);
    }

    void save(@NonNull String eventId, @NonNull Runnable runnable) {
        taskByLiveEventId.put(eventId, sportEventScheduledExecutorService.scheduleAtFixedRate(runnable, 0, 10, SECONDS));
    }
}
