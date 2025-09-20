package dev.donaldsonblack.cura.listener;

import dev.donaldsonblack.cura.event.ApiAccessEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApiAccessEventListener {

  private static final Logger logger = LoggerFactory.getLogger(ApiAccessEventListener.class);

  // ANSI colors
  private static final String RESET = "\u001B[0m";
  private static final String GREEN = "\u001B[32m"; // GET / 2xx
  private static final String BLUE = "\u001B[34m"; // POST
  private static final String YELLOW = "\u001B[33m"; // PUT / 4xx
  private static final String RED = "\u001B[31m"; // DELETE / 5xx
  private static final String CYAN = "\u001B[36m"; // endpoint
  private static final String MAGENTA = "\u001B[35m"; // user name
  private static final String WHITE = "\u001B[37m"; // user id / ip
  private static final String GRAY = "\u001B[90m"; // timestamp

  private static final DateTimeFormatter TS_FMT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @EventListener
  public void handleApiAccess(ApiAccessEvent e) {
    // Color by HTTP method
    String methodColor =
        switch (e.getMethod()) {
          case "GET" -> GREEN;
          case "POST" -> BLUE;
          case "PUT" -> YELLOW;
          case "DELETE" -> RED;
          default -> CYAN;
        };

    // Color by status range
    String statusColor =
        (e.getStatusCode() >= 500) ? RED : (e.getStatusCode() >= 400) ? YELLOW : GREEN;

    String ts = LocalDateTime.now().format(TS_FMT);

    // Build colored parts
    String coloredTime = GRAY + ts + RESET;
    String coloredMethod = "[" + methodColor + e.getMethod() + RESET + "]";
    String coloredEndpoint = CYAN + e.getEndpoint() + RESET;
    String coloredUser = MAGENTA + e.getUserName() + RESET;
    String coloredUserId = WHITE + e.getUserid() + RESET;
    String coloredIp = WHITE + e.getIp() + RESET;
    String coloredStatus = statusColor + e.getStatusCode() + RESET;

    // Final message
    String msg =
        String.format(
            "%s %s → %s | User: %s (ID: %s) | IP: %s | Status: %s (%dms)",
            coloredTime,
            coloredMethod,
            coloredEndpoint,
            coloredUser,
            coloredUserId,
            coloredIp,
            coloredStatus,
            e.getDuration());

    // Level based on status
    if (e.getStatusCode() >= 500) {
      logger.error(msg);
    } else if (e.getStatusCode() >= 400) {
      logger.warn(msg);
    } else {
      logger.info(msg);
    }
  }
}
