package com.dblck.curas.dto.equipment;

import jakarta.validation.constraints.NotBlank;

public record EquipmentCreateRequest(
    @NotBlank Integer deptId,
    @NotBlank String serial,
    @NotBlank String model,
    @NotBlank String name) {}
