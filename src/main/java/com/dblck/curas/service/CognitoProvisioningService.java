package com.dblck.curas.service;

import com.dblck.curas.model.User;
import com.dblck.curas.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CognitoProvisioningService {
	private final Logger logger = LoggerFactory.getLogger(CognitoProvisioningService.class);
	private final UserRepository userRepository;
	private static final String USER_SUB_CACHE = "userBySub";

	@Transactional
	@Cacheable(value = USER_SUB_CACHE, key = "#p0")
	public User ensureUserExists(UUID sub, String email, String fName, String lName) {
		Optional<User> existing = userRepository.findBySub(sub);
		if (existing.isPresent()) {
			return existing.get();
		}

		User u = User.builder()
				.sub(sub)
				.email(email != null ? email : "")
				.fname(fName != null ? fName : "")
				.lname(lName != null ? lName : "")
				.build();

		try {
			User saved = userRepository.saveAndFlush(u);
			logger.info("Provisioned user for cognito sub={}", sub);
			return saved;
		} catch (DataIntegrityViolationException ex) {
			logger.debug("Race on insert for cognito sub={}, re-querying", sub);
			return userRepository.findBySub(sub).orElseThrow(() -> ex);
		}
	}
}
