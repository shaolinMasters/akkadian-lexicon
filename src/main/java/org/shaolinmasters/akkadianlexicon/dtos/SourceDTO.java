package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SourceDTO {

  @NotEmpty
  private String title;
  @NotEmpty
  private String catalogueRef;
  @NotEmpty
  private String bibliography;
  @NotEmpty
  private String text;

  private Long kingId;

}
