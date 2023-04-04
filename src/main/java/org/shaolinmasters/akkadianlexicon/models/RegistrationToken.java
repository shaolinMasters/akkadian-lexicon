package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RegistrationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "token")
  private String tokenString;

  @OneToOne(cascade = CascadeType.REMOVE, targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  private LocalDateTime expiryDateTime;

  public RegistrationToken() {
    super();
  }

  public RegistrationToken(String tokenString, User user, LocalDateTime expiryDateTime) {
    super();
    this.tokenString = tokenString;
    this.user = user;
    this.expiryDateTime = expiryDateTime;
  }
}
