package dev.donaldsonblack.cura.model;

import java.util.List;
import dev.donaldsonblack.cura.model.DepartmentOption;

public record ChecklistFormData(
		List<DepartmentOption> departments,
		List<String> types // e.g., ["Daily","Weekly","Monthly"]
) {
}
