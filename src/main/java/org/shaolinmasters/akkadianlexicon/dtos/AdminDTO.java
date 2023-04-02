package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.shaolinmasters.akkadianlexicon.models.enums.Degree;
import org.shaolinmasters.akkadianlexicon.models.enums.Institution;

@Getter
@Setter
@ToString
public class AdminDTO {
  private String email;
  private String name;
//  private Institution institution;
//  private Degree degree;
}
