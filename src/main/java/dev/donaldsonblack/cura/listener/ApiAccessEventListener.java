package dev.donaldsonblack.cura.listener;

import dev.donaldsonblack.cura.event.ApiAccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApiAccessEventListener {

	private static final Logger logger = LoggerFactory.getLogger(ApiAccessEventListener.class);

	// ANSI color codes for HTTP methods
	private static final String RESET = "\u001B[0m";
	private static final String GREEN = "\u001B[32m"; // GET
	private static final String BLUE = "\u001B[34m"; // POST
	private static final String YELLOW = "\u001B[33m"; // PUT
	private static final String RED = "\u001B[31m"; // DELETE
	private static final String CYAN = "\u001B[36m"; // Others

	@EventListener
	public void handleApiAccess(ApiAccessEvent event) {
		String method = event.getMethod();
		String userId = event.getUserId();
		String endpoint = event.getEndpoint();

		String color = switch (method) {
			case "GET" -> GREEN;
			case "POST" -> BLUE;
			case "PUT" -> YELLOW;
			case "DELETE" -> RED;
			default -> CYAN;
		};

		String coloredMethod = String.format("[%s%s%s]", color, method, RESET);

		String logMessage = String.format("%s %s by User %s", coloredMethod, endpoint, userId);
		logger.info(logMessage);
	}
}
