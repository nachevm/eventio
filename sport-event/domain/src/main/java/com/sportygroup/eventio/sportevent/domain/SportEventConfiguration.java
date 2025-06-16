package com.sportygroup.eventio.sportevent.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SportEventProperties.class)
class SportEventConfiguration {

  @Bean
  ScheduledExecutorService sportEventScheduledExecutorService() {
    return Executors.newScheduledThreadPool(10);
  }

  @Bean
  Map<String, ScheduledFuture<?>> taskByEventId() {
    return new ConcurrentHashMap<>();
  }
}
