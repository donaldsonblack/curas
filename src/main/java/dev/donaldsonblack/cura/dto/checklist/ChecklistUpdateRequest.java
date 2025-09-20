package dev.donaldsonblack.cura.dto.checklist;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;

public record ChecklistUpdateRequest(
    Optional<String> name,
    Optional<String> description,
    Optional<String> type,
    Optional<Integer> deptId,
    Optional<Integer> equipId,
    Optional<Integer> authId,
    Optional<JsonNode> questions) {}
