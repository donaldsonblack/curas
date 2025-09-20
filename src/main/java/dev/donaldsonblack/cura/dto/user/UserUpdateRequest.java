package dev.donaldsonblack.cura.dto.user;

import java.util.Optional;
import java.util.UUID;

import dev.donaldsonblack.cura.model.Role;

public record UserUpdateRequest(
    Optional<String> fname, Optional<String> lname, Optional<String> email, Optional<UUID> sub, Optional<Role> role) {}
