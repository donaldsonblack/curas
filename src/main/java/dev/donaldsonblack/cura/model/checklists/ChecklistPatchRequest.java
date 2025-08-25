package dev.donaldsonblack.cura.model.checklists;

import java.time.Instant;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * PATCH payload for updating a Checklist.
 * 
 * All fields are optional — only those set will be applied.
 */
public class ChecklistPatchRequest {

	private Integer id; // optional, usually comes from path param
	private String name;
	private String description;
	private String type;
	private Integer departmentId;
	private Integer equipmentId;
	private Integer authorId;
	private Instant created; // usually left null (DB manages)
	private JsonNode questions;

	public ChecklistPatchRequest() {
	}

	private ChecklistPatchRequest(Builder b) {
		this.id = b.id;
		this.name = b.name;
		this.description = b.description;
		this.type = b.type;
		this.departmentId = b.departmentId;
		this.equipmentId = b.equipmentId;
		this.authorId = b.authorId;
		this.created = b.created;
		this.questions = b.questions;
	}

	// ---------- Getters ----------
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public Instant getCreated() {
		return created;
	}

	public JsonNode getQuestions() {
		return questions;
	}

	// ---------- Builder ----------
	public static class Builder {
		private Integer id;
		private String name;
		private String description;
		private String type;
		private Integer departmentId;
		private Integer equipmentId;
		private Integer authorId;
		private Instant created;
		private JsonNode questions;

		public Builder id(Integer id) {
			this.id = id;
			return this;
		}

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

		public Builder created(Instant created) {
			this.created = created;
			return this;
		}

		public Builder questions(JsonNode questions) {
			this.questions = questions;
			return this;
		}

		public ChecklistPatchRequest build() {
			return new ChecklistPatchRequest(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	// ---------- Helper: apply patch onto an existing Checklist ----------
	public Checklist applyTo(Checklist existing) {
		if (this.name != null)
			existing.setName(this.name);
		if (this.description != null)
			existing.setDescription(this.description);
		if (this.type != null)
			existing.setType(this.type);
		if (this.departmentId != null)
			existing.setDepartmentId(this.departmentId);
		if (this.equipmentId != null)
			existing.setEquipmentId(this.equipmentId);
		if (this.authorId != null)
			existing.setAuthorId(this.authorId);
		if (this.created != null)
			existing.setCreated(this.created);
		if (this.questions != null)
			existing.setQuestions(this.questions);
		return existing;
	}
}
