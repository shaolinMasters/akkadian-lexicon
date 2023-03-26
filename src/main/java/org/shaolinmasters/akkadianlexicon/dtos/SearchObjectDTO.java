package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchObjectDTO {

  private String option;

  private String word;

  private String king;

  private String title;
}
