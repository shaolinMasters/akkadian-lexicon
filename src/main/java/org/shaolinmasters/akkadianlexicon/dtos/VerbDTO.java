package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.shaolinmasters.akkadianlexicon.models.enums.VerbalStem;
import org.shaolinmasters.akkadianlexicon.models.enums.VowelClass;

@Getter
@Setter
@ToString
public class VerbDTO {

  private String meaning;

  private String sumerianForm;

  private String nominative;

  private VowelClass vowelClass;

  private VerbalStem verbalStem;
}
