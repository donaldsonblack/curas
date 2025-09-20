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

  public void publishApiAccess(
      String id,
      String endpoint,
      String method,
      String ip,
      String name,
      Integer statuscode,
      long duration) {
    ApiAccessEvent event = new ApiAccessEvent(id, endpoint, method, ip, name, statuscode, duration);
    publisher.publishEvent(event);
  }
}
