package dev.donaldsonblack.cura.model.checklists;

import java.time.Instant;
import com.fasterxml.jackson.databind.JsonNode;

public class ChecklistDetail {
	// Checklist
	private final int id;
	private final String name;
	private final String description;
	private final String type;
	private final Instant created;
	private final JsonNode questions;

	// Equipment
	private final int equipmentId;
	private final String equipmentName;
	private final String equipmentModel;
	private final String equipmentSerial;

	// Department
	private final int departmentId;
	private final String departmentName;

	// Author
	private final String author;
	private final int authorId;

	// Private constructor - only accessible through Builder
	private ChecklistDetail(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.type = builder.type;
		this.created = builder.created;
		this.questions = builder.questions;
		this.equipmentId = builder.equipmentId;
		this.equipmentName = builder.equipmentName;
		this.equipmentModel = builder.equipmentModel;
		this.equipmentSerial = builder.equipmentSerial;
		this.departmentId = builder.departmentId;
		this.departmentName = builder.departmentName;
		this.author = builder.author;
		this.authorId = builder.authorId;
	}

	// Getters
	public int getId() {
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

	public Instant getCreated() {
		return created;
	}

	public JsonNode getQuestions() {
		return questions;
	}

	public int getEquipmentId() {
		return equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public String getEquipmentModel() {
		return equipmentModel;
	}

	public String getEquipmentSerial() {
		return equipmentSerial;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public String getAuthor() {
		return author;
	}

	public int getAuthorId() {
		return authorId;
	}

	// Builder inner class
	public static class Builder {
		private int id;
		private String name;
		private String description;
		private String type;
		private Instant created;
		private JsonNode questions;
		private int equipmentId;
		private String equipmentName;
		private String equipmentModel;
		private String equipmentSerial;
		private int departmentId;
		private String departmentName;
		private String author;
		private int authorId;

		public Builder id(int id) {
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

		public Builder created(Instant created) {
			this.created = created;
			return this;
		}

		public Builder questions(JsonNode questions) {
			this.questions = questions;
			return this;
		}

		public Builder equipmentId(int equipmentId) {
			this.equipmentId = equipmentId;
			return this;
		}

		public Builder equipmentName(String equipmentName) {
			this.equipmentName = equipmentName;
			return this;
		}

		public Builder equipmentModel(String equipmentModel) {
			this.equipmentModel = equipmentModel;
			return this;
		}

		public Builder equipmentSerial(String equipmentSerial) {
			this.equipmentSerial = equipmentSerial;
			return this;
		}

		public Builder departmentId(int departmentId) {
			this.departmentId = departmentId;
			return this;
		}

		public Builder departmentName(String departmentName) {
			this.departmentName = departmentName;
			return this;
		}

		public Builder author(String author) {
			this.author = author;
			return this;
		}

		public Builder authorId(int authorId) {
			this.authorId = authorId;
			return this;
		}

		public ChecklistDetail build() {
			return new ChecklistDetail(this);
		}
	}

	// Static entry point for builder
	public static Builder builder() {
		return new Builder();
	}
}
