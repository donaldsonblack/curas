package dev.donaldsonblack.cura.model.checklists;

import java.time.Instant;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Incoming payload for creating a Checklist.
 * Server/DB will generate id and created timestamp.
 */
public class ChecklistCreateRequest {

	@NotBlank
	@Size(max = 200)
	private String name;

	@Size(max = 1000)
	private String description;

	@NotBlank
	@Size(max = 50)
	private String type;

	@NotNull
	private Integer departmentId;

	@NotNull
	private Integer equipmentId;

	@NotNull
	private Integer authorId;

	@NotNull
	private JsonNode questions;

	// ---- Constructors for Jackson / programmatic use ----
	public ChecklistCreateRequest() {
	}

	private ChecklistCreateRequest(Builder b) {
		this.name = b.name;
		this.description = b.description;
		this.type = b.type;
		this.departmentId = b.departmentId;
		this.equipmentId = b.equipmentId;
		this.authorId = b.authorId;
		this.questions = b.questions;
	}

	// ---- Getters & Setters (needed for Jackson) ----
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public JsonNode getQuestions() {
		return questions;
	}

	public void setQuestions(JsonNode questions) {
		this.questions = questions;
	}

	// ---- Builder (convenient for tests / internal construction) ----
	public static class Builder {
		private String name;
		private String description;
		private String type;
		private Integer departmentId;
		private Integer equipmentId;
		private Integer authorId;
		private JsonNode questions;

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder departmentId(Integer departmentId) {
			this.departmentId = departmentId;
			return this;
		}

		public Builder equipmentId(Integer equipmentId) {
			this.equipmentId = equipmentId;
			return this;
		}

		public Builder authorId(Integer authorId) {
			this.authorId = authorId;
			return this;
		}

		public Builder questions(JsonNode questions) {
			this.questions = questions;
			return this;
		}

		public ChecklistCreateRequest build() {
			return new ChecklistCreateRequest(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	// ---- Helper: convert to domain entity WITHOUT id/created ----
	public Checklist toChecklistEntity() {
		Checklist c = new Checklist();
		c.setName(this.name);
		c.setDescription(this.description);
		c.setType(this.type);
		c.setDepartmentId(this.departmentId);
		c.setEquipmentId(this.equipmentId);
		c.setAuthorId(this.authorId);
		// created is omitted (DB default NOW()); id is omitted (DB generated)
		c.setQuestions(this.questions);
		return c;
	}
}
