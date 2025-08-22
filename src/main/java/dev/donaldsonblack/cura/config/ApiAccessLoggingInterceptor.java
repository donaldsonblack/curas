package dev.donaldsonblack.cura.config;

import dev.donaldsonblack.cura.service.ApiEventPublisher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiAccessLoggingInterceptor implements HandlerInterceptor {

	private final ApiEventPublisher eventPublisher;

	public ApiAccessLoggingInterceptor(ApiEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof Jwt jwt) {
			String userId = jwt.getSubject();
			String method = request.getMethod();
			String endpoint = request.getRequestURI();
			String ip = extractClientIp(request);

			eventPublisher.publishApiAccess(userId, endpoint, method, ip);
		}

		return true;
	}

	private String extractClientIp(HttpServletRequest request) {
		String xf = request.getHeader("X-Forwarded-For");
		if (xf != null && !xf.isBlank()) {
			return xf.split(",")[0];
		}
		return request.getRemoteAddr();
	}
}
