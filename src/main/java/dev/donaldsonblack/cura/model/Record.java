package dev.donaldsonblack.cura.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;

public class Record {
  private Integer id;
  private Integer checklistId;
  private Integer authorId;
  private Instant created;
  private JsonNode answers;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getChecklistId() {
    return checklistId;
  }

  public void setChecklistId(Integer checklistId) {
    this.checklistId = checklistId;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

  public Instant getCreated() {
    return created;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }

  public JsonNode getAnswers() {
    return answers;
  }

  public void setAnswers(JsonNode answers) {
    this.answers = answers;
  }
}
