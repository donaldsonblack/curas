package dev.donaldsonblack.cura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import dev.donaldsonblack.cura.config.JsonViews;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
	@JsonView(JsonViews.userMinimal.class)
  private Integer id;

	@NotNull
  @Column(name = "cognito_sub")
  private UUID sub;

  // @NotBlank
  // @NotNull
	@JsonView(JsonViews.userMinimal.class)
  private String email;

  // @NotBlank
  // @NotNull
  @Column(name = "first_name")
	@JsonView(JsonViews.userMinimal.class)
  private String fname;

  // @NotBlank
  // @NotNull
  @Column(name = "last_name")
	@JsonView(JsonViews.userMinimal.class)
  private String lname;

  // @NotBlank
  // @NotNull
  @Column(name = "created", insertable = false, updatable = false)
  private Instant created;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonIgnore   
	@Builder.Default
	private Set<UserDepartment> memberships = new HashSet<>();
}

// create table users
// (
//     id          serial
//         primary key,
//     cognito_sub uuid not null
//         unique,
//     email       text not null
//         unique,
//     created     timestamp with time zone default now(),
//     first_name  text not null,
//     last_name   text
// );

// alter table users
//     owner to postgres;
