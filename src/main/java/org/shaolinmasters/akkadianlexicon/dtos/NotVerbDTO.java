package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NotVerbDTO {

  @NotEmpty(message = "Meaning must not be empty")
  private String meaning;

  private String sumerianForm;
  @NotEmpty(message = "Nominative must not be empty")
  private String nominative;

  private String wordClass;
}
