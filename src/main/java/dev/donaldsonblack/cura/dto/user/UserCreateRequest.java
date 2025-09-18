package dev.donaldsonblack.cura.dto.user;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(@NotBlank String fname, @NotBlank String lname, @NotBlank @Email String email,
		@NotNull UUID sub) {
} 
