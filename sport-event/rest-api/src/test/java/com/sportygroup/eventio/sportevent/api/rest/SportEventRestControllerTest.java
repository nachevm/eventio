package com.sportygroup.eventio.sportevent.api.rest;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sportygroup.eventio.sportevent.api.SportEventService;
import com.sportygroup.eventio.sportevent.api.SportEventStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class SportEventRestControllerTest {

  private MockMvc mockMvc;
  @Mock
  private SportEventService sportEventService;

  @BeforeEach
  void initController() {
    mockMvc = MockMvcBuilders.standaloneSetup(new SportEventRestController(sportEventService))
        .defaultRequest(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
        .alwaysDo(MockMvcResultHandlers.print())
        .build();
  }

  @Test
  void updateStatus_shouldReturnOk_whenValidInput() throws Exception {
    mockMvc.perform(post("/events/status")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "eventId": "1",
                    "status": "LIVE"
                }
                """))
        .andExpect(status().isOk());

    verify(sportEventService).updateStatus("1", SportEventStatus.LIVE);
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "invalid body",
      "{}",
      """
          {
              "eventId": "1",
              "status": "INVALID"
          }
          """,
      """
          {
              "eventId": "1"
          }
          """,
      """
          {
              "status": "LIVE"
          }
          """,
      """
          {
              "eventId": " ",
              "status": "LIVE"
          }
          """,
  })
  void updateStatus_shouldReturnBadRequest(String content) throws Exception {
    mockMvc.perform(post("/events/status")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
        .andExpect(status().isBadRequest());
  }
}
