package dev.donaldsonblack.cura.dto.checklist;

import com.fasterxml.jackson.databind.JsonNode;
import software.amazon.awssdk.annotations.NotNull;

public record ChecklistCreateRequest(
  @NotNull String name,
  @NotNull String description,
  @NotNull String type,
  @NotNull Integer deptId,
  @NotNull Integer equipId,
  @NotNull Integer authId,
  @NotNull JsonNode questions
) {}
