package dev.donaldsonblack.cura.dto.equipment;

import java.util.Optional;

public record EquipmentUpdateRequest(Optional<Integer> deptId, Optional<String> serial, Optional<String> model, Optional<String> name) {
}
