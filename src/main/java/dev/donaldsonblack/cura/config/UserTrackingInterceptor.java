// package dev.donaldsonblack.cura.config;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.HandlerInterceptor;
// import org.springframework.beans.factory.annotation.Autowired;
// import java.util.UUID;
// import dev.donaldsonblack.cura.service.UserService;
// @Component
// public class UserTrackingInterceptor implements HandlerInterceptor {
// 	@Autowired
// 	private UserService userService;
// 	@Override
// 	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
// 		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
// 		if (principal instanceof Jwt jwt) {
// 			String sub = jwt.getClaimAsString("sub");
// 			String name = jwt.getClaimAsString("username"); // or email/preferred_username
// 			userService.ensureUserExists(UUID.fromString(sub), name);
// 		}
// 		return true; // continue processing
// 	}
// }
