package dev.donaldsonblack.cura.model.records;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class RecordPatchRequest {

	private Integer checklistId; // optional
	private Integer authorId;    // optional
	private JsonNode answers;    // optional

	public RecordPatchRequest() {
	}

	public RecordPatchRequest(Integer checklistId, Integer authorId, JsonNode answers) {
		this.checklistId = checklistId;
		this.authorId = authorId;
		this.answers = answers;
	}

	public Integer getChecklistId() { return this.checklistId; }
	public void setChecklistId(Integer checklistId) { this.checklistId = checklistId; }

	public Integer getAuthorId() { return this.authorId; }
	public void setAuthorId(Integer authorId) { this.authorId = authorId; }

	public JsonNode getAnswers() { return this.answers; }
	public void setAnswers(JsonNode answers) { this.answers = answers; }

	public static class Builder {
		private Integer checklistId;
		private Integer authorId;
		private JsonNode answers;

		public Builder checklistId(Integer checklistId) { this.checklistId = checklistId; return this; }
		public Builder authorId(Integer authorId) { this.authorId = authorId; return this; }
		public Builder answers(JsonNode answers) { this.answers = answers; return this; }

		public RecordPatchRequest build() {
			return new RecordPatchRequest(checklistId, authorId, answers);
		}
	}

	public static Builder builder() { return new Builder(); }
}
