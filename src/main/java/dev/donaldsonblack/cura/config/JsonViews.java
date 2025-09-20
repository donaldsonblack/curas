package dev.donaldsonblack.cura.config;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class JsonViews {
  public interface userMinimal extends userDepartment {}

  public interface departmentMinimal extends userDepartment {}

  public interface userDepartment {}
}
