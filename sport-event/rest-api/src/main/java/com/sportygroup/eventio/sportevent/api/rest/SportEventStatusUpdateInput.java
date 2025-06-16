package com.sportygroup.eventio.sportevent.api.rest;

import com.sportygroup.eventio.sportevent.api.SportEventStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record SportEventStatusUpdateInput(
    @NotNull @NotBlank String eventId,
    @NotNull SportEventStatus status) {

}
