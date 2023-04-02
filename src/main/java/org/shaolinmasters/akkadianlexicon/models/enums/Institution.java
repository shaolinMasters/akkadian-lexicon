package org.shaolinmasters.akkadianlexicon.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum Institution {
  EXAMPLE("example");
  private final String institutionName;
}
