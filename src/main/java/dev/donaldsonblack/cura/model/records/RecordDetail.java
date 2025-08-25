package dev.donaldsonblack.cura.model.records;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class RecordDetail {

    private int id;
    private int checklistId;
    private int authorId;

    private Instant created;
    private JsonNode answers;

    private String authorName;
    private String equipmentName;
    private String equipmentModel;
    private String equipmentSerial;
    private String departmentName;

    public RecordDetail() {
    }

    public RecordDetail(int id, int checklistId, int authorId,
                        Instant created, JsonNode answers,
                        String authorName,
                        String equipmentName, String equipmentModel,
                        String equipmentSerial, String departmentName) {
        this.id = id;
        this.checklistId = checklistId;
        this.authorId = authorId;
        this.created = created;
        this.answers = answers;
        this.authorName = authorName;
        this.equipmentName = equipmentName;
        this.equipmentModel = equipmentModel;
        this.equipmentSerial = equipmentSerial;
        this.departmentName = departmentName;
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

    public String getAuthorName() { return this.authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getEquipmentName() { return this.equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

    public String getEquipmentModel() { return this.equipmentModel; }
    public void setEquipmentModel(String equipmentModel) { this.equipmentModel = equipmentModel; }

    public String getEquipmentSerial() { return this.equipmentSerial; }
    public void setEquipmentSerial(String equipmentSerial) { this.equipmentSerial = equipmentSerial; }

    public String getDepartmentName() { return this.departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public static class Builder {
        private int id;
        private int checklistId;
        private int authorId;
        private Instant created;
        private JsonNode answers;
        private String authorName;
        private String equipmentName;
        private String equipmentModel;
        private String equipmentSerial;
        private String departmentName;

        public Builder id(int id) { this.id = id; return this; }
        public Builder checklistId(int checklistId) { this.checklistId = checklistId; return this; }
        public Builder authorId(int authorId) { this.authorId = authorId; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder answers(JsonNode answers) { this.answers = answers; return this; }
        public Builder authorName(String authorName) { this.authorName = authorName; return this; }
        public Builder equipmentName(String equipmentName) { this.equipmentName = equipmentName; return this; }
        public Builder equipmentModel(String equipmentModel) { this.equipmentModel = equipmentModel; return this; }
        public Builder equipmentSerial(String equipmentSerial) { this.equipmentSerial = equipmentSerial; return this; }
        public Builder departmentName(String departmentName) { this.departmentName = departmentName; return this; }

        public RecordDetail build() {
            return new RecordDetail(id, checklistId, authorId,
                    created, answers,
                    authorName, equipmentName, equipmentModel,
                    equipmentSerial, departmentName);
        }
    }

    public static Builder builder() { return new Builder(); }
}
