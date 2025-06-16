package com.sportygroup.eventio.messaging.rabbitmq;

import com.sportygroup.eventio.messaging.api.MessagePublisher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.annotation.Retryable;

/**
 * RabbitMessagePublisher is an implementation of {@link MessagePublisher}
 * that uses RabbitMQ as the underlying messaging system.
 */
@Slf4j
@RequiredArgsConstructor
class RabbitMessagePublisher implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Retryable
    @Override
    public void publish(@NonNull String key, @NonNull Object message) {
        log.debug("Publishing {}:{}", key, message);
        rabbitTemplate.convertAndSend(key, message);
    }
}
