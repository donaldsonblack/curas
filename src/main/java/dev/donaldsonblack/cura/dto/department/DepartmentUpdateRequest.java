package dev.donaldsonblack.cura.dto.department;

import java.util.Optional;

public record DepartmentUpdateRequest(Optional<String> name, Optional<Integer> parent, Optional<String> location) {
}
