package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KingDTO {
  private String kingName;
  private Short regnalYearFrom;
  private Short regnalYearTo;

  private Long sourceId;
}
