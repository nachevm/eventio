package com.sportygroup.eventio.score.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sportygroup.eventio.score.api.ScoreData;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

class ScoreRestServiceTest {

  private MockWebServer mockWebServer;
  private ScoreRestService scoreRestService;

  @BeforeEach
  void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();

    scoreRestService = new ScoreRestService(WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build());
  }

  @AfterEach
  void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  void findById_shouldReturnScoreData_whenValidIdProvided() throws Exception {
    mockWebServer.enqueue(new MockResponse()
        .setResponseCode(200)
        .setHeader("Content-Type", "application/json")
        .setBody("""
            {
                "eventId": "1",
                "currentScore": "0:0"
            }
            """));

    assertThat(scoreRestService.findById("1")).isEqualTo(new ScoreData("1", "0:0"));
    var recordedRequest = mockWebServer.takeRequest();
    assertThat(recordedRequest.getPath()).isEqualTo("/1");
    assertThat(recordedRequest.getMethod()).isEqualTo("GET");
  }

  @Test
  void findById_shouldThrowException_whenScoreNotFound() throws Exception {
    mockWebServer.enqueue(new MockResponse()
        .setResponseCode(404)
        .setHeader("Content-Type", "application/json")
        .setBody("{}"));

    assertThrows(WebClientResponseException.class, () -> scoreRestService.findById("1"));
    var recordedRequest = mockWebServer.takeRequest();
    assertThat(recordedRequest.getPath()).isEqualTo("/1");
    assertThat(recordedRequest.getMethod()).isEqualTo("GET");
  }

  @Test
  void findById_shouldThrowException_whenServerError() throws Exception {
    mockWebServer.enqueue(new MockResponse()
        .setResponseCode(500)
        .setHeader("Content-Type", "application/json")
        .setBody("Internal Server Error"));

    assertThrows(WebClientResponseException.class, () -> scoreRestService.findById("1"));
    var recordedRequest = mockWebServer.takeRequest();
    assertThat(recordedRequest.getPath()).isEqualTo("/1");
    assertThat(recordedRequest.getMethod()).isEqualTo("GET");
  }
}
