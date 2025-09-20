package dev.donaldsonblack.cura.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonView;

import dev.donaldsonblack.cura.config.JsonViews;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDepartment {

  @EmbeddedId
  private UserDepartmentId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id", nullable = false)
	@JsonView(JsonViews.userMinimal.class)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("departmentId")
  @JoinColumn(name = "department_id", nullable = false)
	@JsonView(JsonViews.departmentMinimal.class)
  private Department department;

	// TODO role enum with weight?
	//  @Column(nullable = false)
	// @JsonView(JsonViews.userDepartment.class)
	//  private String role;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@JsonView(JsonViews.userDepartment.class)
	private Role role;

  @Column(nullable = false, updatable = false)
  private Instant created;
}
