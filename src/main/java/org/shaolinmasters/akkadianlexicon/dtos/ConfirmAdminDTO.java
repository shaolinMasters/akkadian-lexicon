package org.shaolinmasters.akkadianlexicon.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConfirmAdminDTO {

  private String tokenString;
  private String password;
  private String confirmPassword;
}
