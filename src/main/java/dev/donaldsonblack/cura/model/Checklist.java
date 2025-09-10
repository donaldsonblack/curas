package dev.donaldsonblack.cura.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(name = "checklists", indexes = {
		@Index(name = "idx_checklists_dept_created", columnList = "department_id, created DESC")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Checklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotBlank
	@Column(nullable = false)
	private String description;

	@NotBlank
	@Column(nullable = false)
	private String type;

	// Foreign keys are nullable (ON DELETE SET NULL in schema)
	@Column(name = "department_id")
	private Integer departmentId;

	@Column(name = "equipment_id")
	private Integer equipmentId;

	@Column(name = "author_id")
	private Integer authorId;

	@Column(name = "created", insertable = false, updatable = false)
	private Instant created;


	@Column(name = "questions", columnDefinition = "jsonb")
	@JdbcTypeCode(SqlTypes.JSON) 
	private JsonNode questions;
}
