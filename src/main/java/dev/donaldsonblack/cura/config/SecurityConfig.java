package dev.donaldsonblack.cura.config;

import dev.donaldsonblack.cura.security.CognitoProvisioningFilter;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final boolean devEnv;
  private final CognitoProvisioningFilter provisioningFilter;

  public SecurityConfig(Environment env, CognitoProvisioningFilter filter) {
    this.devEnv = Arrays.asList(env.getActiveProfiles()).contains("dev");
    this.provisioningFilter = filter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        auth -> {
          if (devEnv) {
            auth.requestMatchers(
                    "/hello", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/docs")
                .permitAll();
          }

          auth.requestMatchers("/api/**").authenticated();
          auth.anyRequest().authenticated();
        });

    http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
    http.addFilterAfter(provisioningFilter, BearerTokenAuthenticationFilter.class);

    return http.build();
  }
}
