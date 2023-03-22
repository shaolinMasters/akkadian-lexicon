package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.models.Word;

@Getter
@Setter
public class SearchObject {

  private String option;

  private Word word;

  private King king;

  private Source source;
}
