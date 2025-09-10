package dev.donaldsonblack.cura.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping()
public class UtilityController {

	@Qualifier("requestMappingHandlerMapping")
	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@GetMapping("/api/endpoints")
	public List<Map<String, String>> listEndpoints() {
		List<Map<String, String>> endpoints = new ArrayList<>();

		handlerMapping.getHandlerMethods().forEach((info, method) -> {
			Set<String> paths;

			if (info.getPathPatternsCondition() != null) {
				paths = info.getPathPatternsCondition()
						.getPatterns()
						.stream()
						.map(Object::toString)
						.collect(Collectors.toSet());
			} else if (info.getPatternsCondition() != null) {
				paths = info.getPatternsCondition().getPatterns();
			} else {
				paths = Set.of("UNKNOWN");
			}

			String methods = info.getMethodsCondition().getMethods().stream()
					.map(Enum::name)
					.reduce((a, b) -> a + ", " + b)
					.orElse("ANY");

			for (String path : paths) {
				endpoints.add(Map.of(
						"method", methods,
						"path", path,
						"handler", method.getBeanType().getSimpleName() + "#" + method.getMethod().getName()));
			}
		});

		return endpoints;
	}

	@GetMapping("/me")
	public String getJwtClaims(@AuthenticationPrincipal Jwt jwt) {
		return jwt.getClaims().toString();
	}

	@GetMapping("/hello")
	public String helloFromServer() {
		return "Hello from server";
	}
}
