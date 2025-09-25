package com.dblck.curas.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CognitoUserInfoClient {

  private String userInfoUri;

  private final RestTemplate restTemplate;

  public CognitoUserInfoClient(@Value("${aws.cognito.domain.userInfo}") String userInfoUri) {
    this.restTemplate = new RestTemplate();
    this.userInfoUri =
        Objects.requireNonNull(userInfoUri, "aws.cognito.userinfo-uri must be configured");
  }

  public Map<String, Object> getUserInfo(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<Map> response =
        restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);

    // copy to mutable map to avoid UnsupportedOperationException
    return new HashMap<>(response.getBody());
  }
}
