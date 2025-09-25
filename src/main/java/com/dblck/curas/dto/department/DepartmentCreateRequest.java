package com.dblck.curas.dto.department;

import jakarta.validation.constraints.NotBlank;

public record DepartmentCreateRequest(
    @NotBlank String name, Integer parent, @NotBlank String location) {}
