package dev.donaldsonblack.cura.model.records;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class RecordCreateRequest {

	private int checklistId;
	private int authorId;
	private JsonNode answers;

	public RecordCreateRequest() {
	}

	public RecordCreateRequest(int checklistId, int authorId, JsonNode answers) {
		this.checklistId = checklistId;
		this.authorId = authorId;
		this.answers = answers;
	}

	public int getChecklistId() { return this.checklistId; }
	public void setChecklistId(int checklistId) { this.checklistId = checklistId; }

	public int getAuthorId() { return this.authorId; }
	public void setAuthorId(int authorId) { this.authorId = authorId; }

	public JsonNode getAnswers() { return this.answers; }
	public void setAnswers(JsonNode answers) { this.answers = answers; }

	public static class Builder {
		private int checklistId;
		private int authorId;
		private JsonNode answers;

		public Builder checklistId(int checklistId) { this.checklistId = checklistId; return this; }
		public Builder authorId(int authorId) { this.authorId = authorId; return this; }
		public Builder answers(JsonNode answers) { this.answers = answers; return this; }

		public RecordCreateRequest build() {
			return new RecordCreateRequest(checklistId, authorId, answers);
		}
	}

	public static Builder builder() { return new Builder(); }
}
