package com.dblck.curas.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ApiAccessEvent {
  private final String userid;
  private final String endpoint;
  private final String method;
  private final String ip;
  private final String userName;
  private final Integer statusCode;
  private final long duration;
}
