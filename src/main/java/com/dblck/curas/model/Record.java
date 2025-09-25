package com.dblck.curas.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Record {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Integer id;

  @Column(name = "checklist_id")
  private Integer checklistId;

  @Column(name = "author_id")
  private Integer authorId;

  @Column(name = "created", insertable = false, updatable = false)
  private Instant created;

  @Column(name = "questions", columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private JsonNode answers;
}

// create table record
// (
// id serial
// primary key,
// checklist_id integer
// references checklists
// on delete cascade,
// author_id integer
// references users
// on delete set null,
// created timestamp default now(),
// answers jsonb
// );
