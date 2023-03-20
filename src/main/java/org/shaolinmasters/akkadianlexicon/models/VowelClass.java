package org.shaolinmasters.akkadianlexicon.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VowelClass {
  AU("a/u"),
  A("a"),
  I("i"),
  U("u");

  private final String className;
}
