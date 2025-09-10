package dev.donaldsonblack.cura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	@Column(name = "department_id")
	private Integer departmentId;

	@NotBlank
	private String serial;

	@NotBlank
	private String model;

	@NotBlank
	private String name;

}

// create table equipment
// (
// id serial
// primary key,
// department_id integer
// references department
// on delete cascade,
// serial text,
// model text,
// name text
// );
