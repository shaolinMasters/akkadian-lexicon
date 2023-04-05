package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

  @Column(nullable = false)
  private String name;

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
  @Exclude
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_authority",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Authority> authorities;

  public User(String email, Set<Authority> authorities) {
    this.email = email;
    this.authorities = authorities;
  }


}
