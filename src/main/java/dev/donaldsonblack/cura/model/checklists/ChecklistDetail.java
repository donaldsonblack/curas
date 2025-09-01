package dev.donaldsonblack.cura.model.checklists;

import java.time.Instant;
import com.fasterxml.jackson.databind.JsonNode;

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

	public ChecklistDetail(
			int id,
			String name,
			String description,
			String type,
			Instant created,
			JsonNode questions,
			int equipmentId,
			String equipmentName,
			String equipmentModel,
			String equipmentSerial,
			int departmentId,
			String departmentName,
			String author,
			int authorId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.created = created;
		this.questions = questions;
		this.equipmentId = equipmentId;
		this.equipmentName = equipmentName;
		this.equipmentModel = equipmentModel;
		this.equipmentSerial = equipmentSerial;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.author = author;
		this.authorId = authorId;
	}

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

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public void setQuestions(JsonNode questions) {
		this.questions = questions;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}

	public void setEquipmentSerial(String equipmentSerial) {
		this.equipmentSerial = equipmentSerial;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
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

	public static Builder builder() {
		return new Builder();
	}
}
