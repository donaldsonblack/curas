package dev.donaldsonblack.cura.service;

import dev.donaldsonblack.cura.event.ApiAccessEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ApiEventPublisher {
  private final ApplicationEventPublisher publisher;

  public ApiEventPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  public void publishApiAccess(String userId, String endpoint, String method, String ip) {
    ApiAccessEvent event = new ApiAccessEvent(userId, endpoint, method, ip);
    publisher.publishEvent(event);
  }
}
