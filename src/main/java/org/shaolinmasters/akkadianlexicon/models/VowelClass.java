package org.shaolinmasters.akkadianlexicon.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VowelClass {
  AU("(a/u)"),
  AA("(a/a)"),
  II("(i/i)"),
  UU("(u/u)");

  private final String className;
}
