package dev.donaldsonblack.cura.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final boolean devEnv;

	public SecurityConfig(Environment env) {
		this.devEnv = Arrays.asList(env.getActiveProfiles()).contains("dev");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			if (devEnv) {
				auth.requestMatchers(
						"/hello",
						"/v3/api-docs/**",
						"/swagger-ui.html",
						"/swagger-ui/**",
						"/docs").permitAll();
			}

			auth.requestMatchers("/api/**").authenticated();
			auth.anyRequest().authenticated();
		});

		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

		return http.build();
	}
}
