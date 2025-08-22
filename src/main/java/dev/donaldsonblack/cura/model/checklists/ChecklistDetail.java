package dev.donaldsonblack.cura.model.checklists;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class ChecklistDetail {
	// Checklist
	private int id;
	private String name;
	private String description;
	private String type;
	private Instant created;
	private JsonNode questions;

	// Equipment
	private int equipmentId;
	private String equipmentName;
	private String equipmentModel;
	private String equipmentSerial;

	// Department
	private int departmentId;
	private String departmentName;

	// Author
	private String author;
	private int authorId;
}
