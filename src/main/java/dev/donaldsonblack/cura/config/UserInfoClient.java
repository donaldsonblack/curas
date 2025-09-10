// package dev.donaldsonblack.cura.config;
//
// import org.springframework.http.HttpHeaders;
// import org.springframework.stereotype.Component;
// import org.springframework.web.reactive.function.client.WebClient;
//
// import java.time.Duration;
//
// @Component
// public class UserInfoClient {
//
//   private final WebClient web;
//
//   public UserInfoClient(WebClient.Builder builder) {
//     // small timeouts so we don’t stall requests
//     this.web = builder
//         .baseUrl("") // we’ll pass absolute URL each call
//         .build();
//   }
//
//   /**
//    * Calls {iss}/oauth2/userInfo with the given access token.
//    * @param issuer e.g. "https://cognito-idp.ap-southeast-2.amazonaws.com/ap-southeast-2_XXXX"
//    * @param accessToken the bearer access token from the request
//    */
//   public UserInfoResponse fetch(String issuer, String accessToken) {
//     try {
//       String url = issuer.endsWith("/") ? issuer + "oauth2/userInfo" : issuer + "/oauth2/userInfo";
//       return web.get()
//           .uri(url)
//           .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//           .retrieve()
//           .bodyToMono(UserInfoResponse.class)
//           .block(Duration.ofMillis(800)); // keep it snappy
//     } catch (Exception e) {
//       return null; // caller decides how to handle
//     }
//   }
//
//   // Map only what you need; add more fields if desired
//   public static record UserInfoResponse(
//       String sub,
//       String email,
//       Boolean email_verified,
//       String name,
//       String preferred_username
//   ) {}
// }
