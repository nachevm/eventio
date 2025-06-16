package com.sportygroup.eventio.messaging.rabbitmq;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("!messaging-rabbitmq")
@Configuration
@PropertySource("classpath:/messaging-rabbitmq-disabled.properties")
class RabbitMessageDisabledConfiguration {
}
