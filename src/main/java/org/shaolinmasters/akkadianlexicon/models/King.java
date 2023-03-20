package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class King {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "regnal_year_from")
  private LocalDate regnalYearFrom;

  @Column(name = "regnal_year_to")
  private LocalDate regnalYearTo;

  @OneToMany(mappedBy = "king")
  private List<Source> sources;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    King king = (King) o;
    return name.equals(king.name)
        && Objects.equals(regnalYearFrom, king.regnalYearFrom)
        && Objects.equals(regnalYearTo, king.regnalYearTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, regnalYearFrom, regnalYearTo);
  }
}
