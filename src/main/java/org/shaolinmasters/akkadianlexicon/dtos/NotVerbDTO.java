package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.shaolinmasters.akkadianlexicon.models.enums.VerbalStem;
import org.shaolinmasters.akkadianlexicon.models.enums.VowelClass;
@Setter
@Getter
@ToString
public class NotVerbDTO {

  private String meaning;

  private String sumerianForm;

  private String nominative;

  private String wordClass;

}
