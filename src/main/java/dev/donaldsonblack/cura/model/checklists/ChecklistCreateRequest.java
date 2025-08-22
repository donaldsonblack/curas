package dev.donaldsonblack.cura.model.checklists;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class ChecklistCreateRequest {

	private String name;
	private String description;

	private String type;

	private int departmentId;
	private int equipmentId;
	private int authorId;

	private String author;
	private Instant created;

	private JsonNode questions;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Integer id) {
		this.departmentId = id;
	}

	public Integer getEquipmentId() {
		return this.equipmentId;
	}

	public void setEquipmentId(Integer id) {
		this.equipmentId = id;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Instant getCreated() {
		return this.created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public JsonNode getQuestions() {
		return this.questions;
	}

	public void setQuestions(JsonNode questions) {
		this.questions = questions;
	}
}
