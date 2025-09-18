package dev.donaldsonblack.cura.model;

import com.fasterxml.jackson.annotation.JsonView;

import dev.donaldsonblack.cura.config.JsonViews;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(JsonViews.departmentMinimal.class)
	private Integer id;

	@NotBlank
	@Column(nullable = false, unique = true)
	@JsonView(JsonViews.departmentMinimal.class)
	private String name;

	@Column(name = "parent_id")
	@JsonView(JsonViews.departmentMinimal.class)
	private Integer parent;

	@Column(name = "location")
	@JsonView(JsonViews.departmentMinimal.class)
	private String location;
}
