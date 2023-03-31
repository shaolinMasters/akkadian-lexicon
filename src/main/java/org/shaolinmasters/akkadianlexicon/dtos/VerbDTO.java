package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.shaolinmasters.akkadianlexicon.models.enums.VerbalStem;

@Getter
@Setter
@ToString
public class VerbDTO {

  private String meaning;

  private String sumerianForm;

  private String nominative;

  private String vowelClass;

  private String verbalStem;



}
