package com.sportygroup.eventio.sportevent.domain;

import com.sportygroup.eventio.sportevent.message.SportEventScoreMessage;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("sport-event")
final class SportEventProperties {

    private String scoreMessagingKey = SportEventScoreMessage.class.getSimpleName();
}
