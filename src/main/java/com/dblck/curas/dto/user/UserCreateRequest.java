package com.dblck.curas.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserCreateRequest(
    @NotBlank String fname,
    @NotBlank String lname,
    @NotBlank @Email String email,
    @NotNull UUID sub) {}
