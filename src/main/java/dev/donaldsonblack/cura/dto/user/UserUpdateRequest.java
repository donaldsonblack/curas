package dev.donaldsonblack.cura.dto.user;

import java.util.Optional;
import java.util.UUID;

public record UserUpdateRequest(
    Optional<String> fname, Optional<String> lname, Optional<String> email, Optional<UUID> sub) {}
