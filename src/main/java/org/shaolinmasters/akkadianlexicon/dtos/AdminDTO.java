package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminDTO {

  @Email
  @NotEmpty(message = "Email must not be empty")
  private String email;
  @NotEmpty(message = "Name must not be empty")
  private String name;
  //  private Institution institution;
  //  private Degree degree;
}
