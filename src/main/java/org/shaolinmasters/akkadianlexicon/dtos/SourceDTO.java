package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SourceDTO {
  @NotEmpty(message = "Title must not be empty.")
  private String title;
  @NotEmpty(message = "Catalogur Reference must not be empty.")
  private String catalogueRef;
  @NotEmpty(message = "Bibliography must not be empty")
  private String bibliography;
  @NotEmpty(message = "Text must not be emtpy")
  private String text;
  @Nullable
  private Long kingId;
}
