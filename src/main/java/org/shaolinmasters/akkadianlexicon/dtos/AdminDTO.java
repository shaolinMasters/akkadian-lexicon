package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.shaolinmasters.akkadianlexicon.models.enums.Degree;
import org.shaolinmasters.akkadianlexicon.models.enums.Institution;

@Getter
@Setter
@ToString
public class AdminDTO {

  @Email
  @NotEmpty(message = "Email must not be empty")
  private String email;
  @NotEmpty(message = "Name must not be empty")
  private String name;
  private Degree degree;
  private Institution institution;

  //  private Institution institution;
  //  private Degree degree;
}
