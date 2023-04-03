package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.shaolinmasters.akkadianlexicon.models.enums.VerbalStem;
import org.shaolinmasters.akkadianlexicon.models.enums.VowelClass;

@Getter
@Setter
@ToString
public class VerbDTO {

  @NotEmpty(message = "meaning must not be empty")
  private String meaning;

  private String sumerianForm;

  @NotEmpty(message = "nominative must not be empty")
  private String nominative;

  @NotNull(message = "Please select a Vowel class")
  private VowelClass vowelClass;

  @NotNull(message = "Please select a Verbal stem")
  private VerbalStem verbalStem;
}
