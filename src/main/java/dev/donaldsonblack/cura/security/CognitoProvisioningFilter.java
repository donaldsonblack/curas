package dev.donaldsonblack.cura.security;

import dev.donaldsonblack.cura.service.CognitoProvisioningService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class CognitoProvisioningFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(CognitoProvisioningFilter.class);

  private final CognitoProvisioningService provisioningService;
  private final CognitoUserInfoClient cognitoUserInfoClient;

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
      throws ServletException, IOException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth instanceof JwtAuthenticationToken jwtAuth) {
      String accessToken = jwtAuth.getToken().getTokenValue();
      logger.debug("Fetching Cognito user info for access token: {}", accessToken);
      String accessSub = jwtAuth.getToken().getClaimAsString("sub");

      Map<String, Object> userInfo = cognitoUserInfoClient.getUserInfo(accessToken);

      if (!userInfo.isEmpty() && !(accessSub == null)) {
        try {
          UUID sub = UUID.fromString(accessSub);

          String email = userInfo.getOrDefault("email", "").toString();
          String fName = userInfo.getOrDefault("given_name", "").toString();
          String lName = userInfo.getOrDefault("family_name", "").toString();

          // Fallback parsing of full name if given_name or family_name missing
          if ((fName.isBlank() || lName.isBlank()) && userInfo.containsKey("name")) {
            String name = userInfo.get("name").toString();
            if (name.contains(" ")) {
              String[] parts = name.split(" ", 2);
              if (fName.isBlank()) fName = parts[0];
              if (lName.isBlank()) lName = parts[1];
            } else if (fName.isBlank()) {
              fName = name;
            }
          }

          provisioningService.ensureUserExists(sub, email, fName, lName);

        } catch (IllegalArgumentException ex) {
          logger.warn("Invalid sub claim from Cognito userinfo: {}", userInfo.get("sub"));
        } catch (Exception ex) {
          logger.warn("Error provisioning user {}: {}", userInfo.get("sub"), ex.getMessage(), ex);
        }
      } else {
        logger.warn("Cognito userinfo response missing 'sub' claim or empty: {}", userInfo);
      }
    }

    filterChain.doFilter(req, res);
  }
}
