package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KingDTO {
  @NotEmpty(message = "Ruler's name must not be empty")
  private String kingName;

  @NotNull(message = "Beginning of reign must not be empty")
  @Size(min = 4, max = 4, message = "Beginning of reign must contain 4 digits")
  private String regnalYearFrom;

  @NotEmpty(message = "End of reign must not be empty")
  @Size(min = 4, max = 4, message = "End of reign must contain 4 digits")
  private String regnalYearTo;

  private Long sourceId;
}
