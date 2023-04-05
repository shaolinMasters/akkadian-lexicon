package org.shaolinmasters.akkadianlexicon.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum VowelClass {
  AU("(a/u)"),
  AA("(a/a)"),
  II("(i/i)"),
  UU("(u/u)");

  private final String className;
}
