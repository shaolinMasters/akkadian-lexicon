package org.shaolinmasters.akkadianlexicon.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum Degree {
  EXAMPLE("example");
  private final String degreeName;
}
