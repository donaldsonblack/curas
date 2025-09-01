package dev.donaldsonblack.cura.model.checklists;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class Checklist {

	private String name;
	private String description;
	private String type;

	private int id;
	private int departmentId;
	private int equipmentId;
	private int authorId;

	private Instant created;
	private JsonNode questions;

	public Checklist() {
	}

	public Checklist(String name, String description, String type, int id, int departmentId,
			int equipmentId, int authorId, Instant created, JsonNode questions) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.id = id;
		this.departmentId = departmentId;
		this.equipmentId = equipmentId;
		this.authorId = authorId;
		this.created = created;
		this.questions = questions;
	}

	private Checklist(Builder builder) {
		this.name = builder.name;
		this.description = builder.description;
		this.type = builder.type;
		this.id = builder.id;
		this.departmentId = builder.departmentId;
		this.equipmentId = builder.equipmentId;
		this.authorId = builder.authorId;
		this.created = builder.created;
		this.questions = builder.questions;
	}

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

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(int id) {
		this.authorId = id;
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

	public static class Builder {
		private String name;
		private String description;
		private String type;
		private int id;
		private int departmentId;
		private int equipmentId;
		private int authorId;
		private Instant created;
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

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder departmentId(int departmentId) {
			this.departmentId = departmentId;
			return this;
		}

		public Builder equipmentId(int equipmentId) {
			this.equipmentId = equipmentId;
			return this;
		}

		public Builder authorId(int authorId) {
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

		public Checklist build() {
			return new Checklist(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
