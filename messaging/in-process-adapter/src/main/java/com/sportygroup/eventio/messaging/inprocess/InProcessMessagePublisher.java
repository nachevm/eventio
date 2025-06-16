package com.sportygroup.eventio.messaging.inprocess;

import com.sportygroup.eventio.messaging.api.MessagePublisher;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link MessagePublisher} that just logs the message currently
 */
@Slf4j
@Profile("messaging-in-process")
@Component
class InProcessMessagePublisher implements MessagePublisher {

    @Override
    public void publish(@NonNull String key, @NonNull Object message) {
        log.info("Publishing {}:{}", key, message);
    }
}
