// package dev.donaldsonblack.cura.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.config.Customizer;
//
//
// @Configuration
// // @EnableWebSecurity
// public class SecurityConfig {
//
// 	// @Bean
// 	// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// 	// 	http
// 	// 			.authorizeHttpRequests(authorize -> authorize
// 	// 					.requestMatchers("/hello").permitAll()
// 	// 					.requestMatchers(
// 	// 							"/v3/api-docs/**",
// 	// 							"/swagger-ui.html",
// 	// 							"/swagger-ui/**",
// 	// 							"/docs" 
// 	// 					).permitAll()
// 	// 					.requestMatchers("/api/**").authenticated()
// 	// 					.anyRequest().authenticated()
// 	// 			)
// 	// 			.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
// 	//
// 	// 	return http.build();
// 	// }
// }
