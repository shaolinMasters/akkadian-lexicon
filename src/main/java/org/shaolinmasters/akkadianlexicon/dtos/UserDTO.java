package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class UserDTO {

  @Email
  @NotBlank(message = "Email field empty")
  private String email;

  @NotBlank
  @Size(min = 5, max = 14)
  private String password;
}
