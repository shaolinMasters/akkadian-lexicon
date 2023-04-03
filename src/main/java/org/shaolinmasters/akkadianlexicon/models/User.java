package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.shaolinmasters.akkadianlexicon.models.enums.Degree;
import org.shaolinmasters.akkadianlexicon.models.enums.Institution;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = true)
  private String password;

  @Column(name = "enabled", columnDefinition = "boolean default false")
  private boolean enabled;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Degree degree;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Institution institution;

  public User(String email, Set<Authority> authorities) {
    this.email = email;
    this.authorities = authorities;
  }

  @Exclude
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_authority",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Authority> authorities;
}
