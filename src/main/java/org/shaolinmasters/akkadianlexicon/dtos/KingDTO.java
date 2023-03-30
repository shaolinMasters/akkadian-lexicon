package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KingDTO {
  @NotEmpty
  private String kingName;

//  @Size(min = 4,max = 4)
  private Short regnalYearFrom;

//  @Size(min = 4,max = 4)
  private Short regnalYearTo;

  private Long sourceId;
}
