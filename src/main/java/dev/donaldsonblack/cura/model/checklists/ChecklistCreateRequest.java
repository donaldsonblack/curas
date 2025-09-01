package dev.donaldsonblack.cura.model.checklists;

import com.fasterxml.jackson.databind.JsonNode;


public class ChecklistCreateRequest {

	private String name;
	private String description;
	private String type;
	private Integer departmentId;
	private Integer equipmentId;
	private Integer authorId;
	private JsonNode questions;

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

	public Checklist toChecklistEntity() {
		Checklist c = new Checklist();
		c.setName(this.name);
		c.setDescription(this.description);
		c.setType(this.type);
		c.setDepartmentId(this.departmentId);
		c.setEquipmentId(this.equipmentId);
		c.setAuthorId(this.authorId);
		c.setQuestions(this.questions);
		return c;
	}
}
