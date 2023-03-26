package org.shaolinmasters.akkadianlexicon.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum VerbalStem {
  G("G"),
  GTN("Gtn"),
  GT("Gt"),
  D("D"),
  DTN("Dtn"),
  DT("Dt"),
  S("Š"),
  STN("Štn"),
  ST("Št"),
  N("N"),
  NTN("Ntn"),
  NT("Nt"),
  RD("RD"),
  SD("ŠD");

  private final String stemName;
}
