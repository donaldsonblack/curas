package dev.donaldsonblack.cura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedHashSet;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@JsonIgnore
	private Department parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Department> children = new LinkedHashSet<>();

	@Transient
	public boolean isRoot() {
		return parent == null;
	}
}
