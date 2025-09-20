package dev.donaldsonblack.cura.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
  member(0, "Member"),
  manager(20, "Manager"),
  admin(50, "Admin"),
  viewer(0, "Viewer"),
  tech(0, "Tech");

  private final Integer weight;
  private final String name;
}
