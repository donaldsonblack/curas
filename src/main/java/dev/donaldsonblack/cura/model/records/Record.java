package dev.donaldsonblack.cura.model.records;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class Record {

	private int id;
	private int checklistId;
	private int authorId;

	private Instant created;
	private JsonNode answers;

	public Record() {
	}

	public Record(int id, int checklistId, int authorId, Instant created, JsonNode answers) {
		this.id = id;
		this.checklistId = checklistId;
		this.authorId = authorId;
		this.created = created;
		this.answers = answers;
	}

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }

	public int getChecklistId() { return this.checklistId; }
	public void setChecklistId(int checklistId) { this.checklistId = checklistId; }

	public int getAuthorId() { return this.authorId; }
	public void setAuthorId(int authorId) { this.authorId = authorId; }

	public Instant getCreated() { return this.created; }
	public void setCreated(Instant created) { this.created = created; }

	public JsonNode getAnswers() { return this.answers; }
	public void setAnswers(JsonNode answers) { this.answers = answers; }

	public static class Builder {
		private int id;
		private int checklistId;
		private int authorId;
		private Instant created;
		private JsonNode answers;

		public Builder id(int id) { this.id = id; return this; }
		public Builder checklistId(int checklistId) { this.checklistId = checklistId; return this; }
		public Builder authorId(int authorId) { this.authorId = authorId; return this; }
		public Builder created(Instant created) { this.created = created; return this; }
		public Builder answers(JsonNode answers) { this.answers = answers; return this; }

		public Record build() {
			return new Record(id, checklistId, authorId, created, answers);
		}
	}

	public static Builder builder() { return new Builder(); }
}
