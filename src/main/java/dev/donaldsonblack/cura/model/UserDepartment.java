package dev.donaldsonblack.cura.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.Instant;
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
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("departmentId")
  @JoinColumn(name = "department_id", nullable = false)
  private Department department;

  @Column(nullable = false)
  private String role;

  @Column(nullable = false, updatable = false)
  private Instant created;
}

// create table user_department
// (
//     user_id       integer                                       not null
//         references users
//             on delete cascade,
//     department_id integer                                       not null
//         references department
//             on delete cascade,
//     role          text                     default 'user'::text not null,
//     created       timestamp with time zone default now()        not null,
//     primary key (user_id, department_id)
// );

// alter table user_department
//     owner to postgres;

// create index idx_user_department_user
//     on user_department (user_id);

// create index idx_user_department_dept
//     on user_department (department_id);
