package dev.donaldsonblack.cura.dto.checklist;

import lombok.Builder;

@Builder
// public record ChecklistTableView(Integer id, String name, String description, String type, String
// departmentName, String equipmentName, String authorName){}
public record ChecklistTableView(
    Integer id,
    String checklist,
    String department,
    String equipment,
    String type,
    String author,
    String description) {}

// c.id, c.name, d.name, e.name, c.type, concat(u.first_name, ' ', u.last_name) as author,
// c.description
