package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private Role role;

  @ManyToMany(cascade= CascadeType.ALL, mappedBy = "authorities")
  @Exclude
  private Set<User> users;
}
