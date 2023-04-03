package org.shaolinmasters.akkadianlexicon.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum Degree {
  PHD("PhD."),
  HABIL("PhD. habil."),
  PROF("Prof.");
  private final String degreeName;
}
