package com.cocoding.playstate.igdb;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class IgdbClient {

  private static final Logger logger = LoggerFactory.getLogger(IgdbClient.class);
  private static final String IGDB_API_BASE_URL = "https://api.igdb.com/v4";
  private static final int IGDB_RETRY_COUNT = 3;
  private static final long IGDB_RETRY_PAUSE_MS = 400L;
  private static final ParameterizedTypeReference<List<Map<String, Object>>> LIST_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {};

  private final IgdbTokenService tokenService;
  private final RestClient restClient;

  public IgdbClient(IgdbTokenService tokenService) {
    this.tokenService = tokenService;
    this.restClient = RestClient.builder().baseUrl(IGDB_API_BASE_URL).build();
  }

  public List<Map<String, Object>> postQuery(String igdbPath, String body) {
    Exception lastError = null;

    for (int attempt = 1; attempt <= IGDB_RETRY_COUNT; attempt++) {
      try {
        List<Map<String, Object>> responseBody =
            restClient
                .post()
                .uri(igdbPath)
                .headers(this::applyIgdbHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(LIST_RESPONSE_TYPE);
        return responseBody != null ? responseBody : Collections.emptyList();
      } catch (Exception e) {
        lastError = e;
        logger.warn(
            "IGDB request failed (try {}/{}): {} - {}",
            attempt,
            IGDB_RETRY_COUNT,
            IGDB_API_BASE_URL + igdbPath,
            e.toString());
        if (e instanceof HttpClientErrorException h && h.getStatusCode().value() == 401) {
          tokenService.invalidateAccessToken();
        }
        if (attempt < IGDB_RETRY_COUNT) {
          sleepQuiet(IGDB_RETRY_PAUSE_MS * attempt);
        }
      }
    }

    logger.error(
        "IGDB still failing after {} tries: {}", IGDB_RETRY_COUNT, IGDB_API_BASE_URL + igdbPath, lastError);
    throw new IgdbException(lastError);
  }

  private void applyIgdbHeaders(HttpHeaders headers) {
    headers.set("Client-ID", tokenService.getClientId());
    headers.set("Authorization", "Bearer " + tokenService.getAccessToken());
  }

  private static void sleepQuiet(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IgdbException(e);
    }
  }
}
