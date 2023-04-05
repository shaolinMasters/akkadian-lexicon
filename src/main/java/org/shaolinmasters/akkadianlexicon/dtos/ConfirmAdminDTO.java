package org.shaolinmasters.akkadianlexicon.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.shaolinmasters.akkadianlexicon.validators.FieldsValueMatch;

@Getter
@Setter
@ToString
public class ConfirmAdminDTO {

  private String tokenString;
  @NotEmpty(message = "Password must not be empty")
  private String password;
  @NotEmpty(message = "Confirm password must not be empty")
  private String confirmPassword;
}
