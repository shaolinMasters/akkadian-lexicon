package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
<<<<<<<< HEAD:src/main/java/org/shaolinmasters/akkadianlexicon/dtos/SourceDTO.java
public class SourceDTO {
========
public class EditSourceDTO {
>>>>>>>> fa7f96c (edit.html, edit.js fajlok módosítva, a megfelelő megjelenítés miatt, validálás beiktatva a DTO osztályba, valamint a Controllerbe is):src/main/java/org/shaolinmasters/akkadianlexicon/dtos/EditSourceDTO.java

  @NotEmpty
  private String title;
  @NotEmpty
  private String catalogueRef;
  @NotEmpty
  private String bibliography;
  @NotEmpty
  private String text;

  private Long kingId;

}
