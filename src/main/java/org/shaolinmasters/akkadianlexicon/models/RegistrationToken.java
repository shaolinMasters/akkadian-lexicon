package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter
@Setter
public class RegistrationToken {

//  @Value( "${email.tokenExpirationDayCount}" )
  @Transient
  private Integer expirationDayCount=1;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  private LocalDateTime expiryDate;

  public RegistrationToken() {
    super();
  }

  public RegistrationToken(String token, User user) {
    super();
    this.token = token;
    this.user = user;
    this.expiryDate = calculateExpiryDate(expirationDayCount);
  }

  private LocalDateTime calculateExpiryDate(Integer days) {
    return LocalDateTime.now().plus(days, ChronoUnit.DAYS);
  }
}
