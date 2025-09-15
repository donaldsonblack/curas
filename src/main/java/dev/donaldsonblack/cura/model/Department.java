package dev.donaldsonblack.cura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import dev.donaldsonblack.cura.config.JsonViews;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(JsonViews.departmentMinimal.class)
  private Integer id;

  @NotBlank
  @Column(nullable = false, unique = true)
	@JsonView(JsonViews.departmentMinimal.class)
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
