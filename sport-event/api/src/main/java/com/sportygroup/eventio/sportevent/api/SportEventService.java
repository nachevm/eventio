package com.sportygroup.eventio.sportevent.api;

public interface SportEventService {

  void updateStatus(String eventId, SportEventStatus status);
}
