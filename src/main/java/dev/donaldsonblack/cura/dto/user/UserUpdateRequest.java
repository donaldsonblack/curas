package dev.donaldsonblack.cura.dto.user;

import java.util.UUID;
import java.util.Optional;

public record UserUpdateRequest(
		Optional<String> fname,
		Optional<String> lname,
		Optional<String> email,
		Optional<UUID> sub) {
}
