// package dev.donaldsonblack.cura.config;
//
// import dev.donaldsonblack.cura.config.UserInfoClient;
// import dev.donaldsonblack.cura.service.UserUpsertService;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//
// import java.io.IOException;
// import java.util.Map;
// import java.util.UUID;
//
// @Component
// public class EnsureUserExistsFilter extends OncePerRequestFilter {
//
//   private static final Logger log = LoggerFactory.getLogger(EnsureUserExistsFilter.class);
//
//   private final UserUpsertService upsert;
//   private final UserInfoClient userInfo;
//
//   public EnsureUserExistsFilter(UserUpsertService upsert, UserInfoClient userInfo) {
//     this.upsert = upsert;
//     this.userInfo = userInfo;
//   }
//
//   @Override
//   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//       throws ServletException, IOException {
//
//     long t0 = System.nanoTime();
//
//     try {
//       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//       if (auth instanceof JwtAuthenticationToken jwtAuth) {
//         Jwt jwt = jwtAuth.getToken();
//         String subStr = jwt.getSubject();              // "sub"
//         String issuer = jwt.getIssuer().toString();    // "iss"
//         String tokenValue = jwt.getTokenValue();       // raw JWT (access token)
//
//         Map<String, Object> claims = jwt.getClaims();
//         String email = (String) claims.get("email");
//         String name  = (String) claims.get("name");
//
//         // If missing, hit UserInfo with the access token
//         if ((email == null || email.isBlank()) || (name == null || name.isBlank())) {
//           UserInfoClient.UserInfoResponse ui = userInfo.fetch(issuer, tokenValue);
//           if (ui != null) {
//             if (email == null || email.isBlank()) email = ui.email();
//             if (name == null  || name.isBlank())  name  = ui.name();
//             // (Optionally) verify sub matches
//             if (ui != null && ui.sub() != null && subStr != null && !ui.sub().equals(subStr)) {
//               log.warn("auth.user_sync userinfo sub mismatch tokenSub={} userInfoSub={}", subStr, ui.sub());
//             }
//           }
//         }
//
//         if (subStr != null) {
//           try {
//             UUID sub = UUID.fromString(subStr);
//             // upsert & get string result ("CREATED"/"UPDATED"/"UNCHANGED")
//             String result = upsert.ensureExistsAndReport(sub, name, email);
//             long ms = (System.nanoTime() - t0) / 1_000_000;
//             log.info("auth.user_sync sub={} email={} path={} method={} result={} took={}ms",
//                 sub, email, request.getRequestURI(), request.getMethod(), result, ms);
//           } catch (IllegalArgumentException bad) {
//             log.error("auth.user_sync invalid sub format '{}' path={} method={}",
//                 subStr, request.getRequestURI(), request.getMethod());
//           }
//         } else {
//           log.warn("auth.user_sync skipped: missing sub claim path={} method={}",
//               request.getRequestURI(), request.getMethod());
//         }
//       }
//     } catch (Exception e) {
//       // Do not block requests on profile sync issues.
//       log.error("auth.user_sync failure path={} method={} msg={}",
//           request.getRequestURI(), request.getMethod(), e.getMessage(), e);
//     }
//
//     chain.doFilter(request, response);
//   }
//
//   @Override
//   protected boolean shouldNotFilter(HttpServletRequest request) {
//     String p = request.getServletPath();
//     return "OPTIONS".equalsIgnoreCase(request.getMethod())
//         || "/actuator/health".equals(p)
//         || "/error".equals(p);
//   }
// }
