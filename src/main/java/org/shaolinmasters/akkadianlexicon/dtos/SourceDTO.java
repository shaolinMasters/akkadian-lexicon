package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SourceDTO {

  private String title;

  private String catalogueRef;

  private String bibliography;

  private String text;

  private Long kingId;
}
