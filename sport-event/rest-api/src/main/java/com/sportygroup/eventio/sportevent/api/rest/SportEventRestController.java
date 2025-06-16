package com.sportygroup.eventio.sportevent.api.rest;

import com.sportygroup.eventio.sportevent.api.SportEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
class SportEventRestController {

    private final SportEventService sportEventService;

    @PostMapping("/status")
    void updateStatus(@RequestBody @Valid SportEventStatusUpdateInput input) {
        sportEventService.updateStatus(input.eventId(), input.status());
    }
}
