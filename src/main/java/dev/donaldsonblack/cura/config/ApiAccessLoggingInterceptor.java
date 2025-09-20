package dev.donaldsonblack.cura.config;

import dev.donaldsonblack.cura.service.ApiEventPublisher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiAccessLoggingInterceptor implements HandlerInterceptor {

  // Request attribute keys (scoped per-request)
  private static final String ATTR_START_TIME = "cura.startTime";
  private static final String ATTR_METHOD = "cura.method";
  private static final String ATTR_ENDPOINT = "cura.endpoint";
  private static final String ATTR_CLIENT_IP = "cura.clientIp";
  private static final String ATTR_USER_ID = "cura.userId";
  private static final String ATTR_USER_NAME = "cura.userName";

  private final ApiEventPublisher eventPublisher;

  public ApiAccessLoggingInterceptor(ApiEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    // Basic request metadata
    request.setAttribute(ATTR_START_TIME, System.currentTimeMillis());
    request.setAttribute(ATTR_METHOD, request.getMethod());
    request.setAttribute(ATTR_ENDPOINT, buildEndpoint(request));
    request.setAttribute(ATTR_CLIENT_IP, extractClientIp(request));

    // Extract user from SecurityContext
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userId = "anonymous";
    String userName = "anonymous";

    if (auth instanceof JwtAuthenticationToken jwtAuth) {
      Jwt jwt = jwtAuth.getToken();
      userId = nonBlank(jwt.getSubject(), "anonymous");
      userName = bestNameFromJwt(jwt);
    } else if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
      userId = nonBlank(jwt.getSubject(), "anonymous");
      userName = bestNameFromJwt(jwt);
    }

    request.setAttribute(ATTR_USER_ID, userId);
    request.setAttribute(ATTR_USER_NAME, userName);

    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable Exception ex) {
    Object startObj = request.getAttribute(ATTR_START_TIME);
    long elapsed = 0L;
    if (startObj instanceof Long start) {
      elapsed = System.currentTimeMillis() - start;
    }

    String method = (String) request.getAttribute(ATTR_METHOD);
    String endpoint = (String) request.getAttribute(ATTR_ENDPOINT);
    String clientIp = (String) request.getAttribute(ATTR_CLIENT_IP);
    String userId = (String) request.getAttribute(ATTR_USER_ID);
    String userName = (String) request.getAttribute(ATTR_USER_NAME);
    int status = response.getStatus();

    // Publish enriched event (status + duration)
    eventPublisher.publishApiAccess(userId, endpoint, method, clientIp, userName, status, elapsed);
  }

  // ——————————————————— helpers ———————————————————

  private static String buildEndpoint(HttpServletRequest req) {
    String uri = req.getRequestURI();
    String qs = req.getQueryString();
    return (qs == null || qs.isBlank()) ? uri : (uri + "?" + qs);
  }

  private static String extractClientIp(HttpServletRequest request) {
    // Respect common proxy headers (first IP from XFF)
    String xff = request.getHeader("X-Forwarded-For");
    if (xff != null && !xff.isBlank()) {
      String first = xff.split(",")[0].trim();
      if (!first.isEmpty()) return first;
    }
    String xri = request.getHeader("X-Real-IP");
    if (xri != null && !xri.isBlank()) return xri.trim();
    return request.getRemoteAddr();
  }

  private static String bestNameFromJwt(Jwt jwt) {
    String name = jwt.getClaimAsString("name");
    if (isNonBlank(name)) return name;

    String preferred = jwt.getClaimAsString("preferred_username");
    if (isNonBlank(preferred)) return preferred;

    String email = jwt.getClaimAsString("email");
    if (isNonBlank(email)) {
      int at = email.indexOf('@');
      return at > 0 ? email.substring(0, at) : email;
    }

    String cognitoUsername = jwt.getClaimAsString("cognito:username");
    if (isNonBlank(cognitoUsername)) return cognitoUsername;

    return "anonymous";
  }

  private static boolean isNonBlank(String v) {
    return v != null && !v.isBlank();
  }

  private static String nonBlank(String v, String fallback) {
    return isNonBlank(v) ? v : fallback;
  }
}
