package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.shaolinmasters.akkadianlexicon.utils.YearAttributeConverter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class King {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "regnal_year_from", columnDefinition = "smallint")
  @Convert(converter = YearAttributeConverter.class)
  private Year regnalYearFrom;

  @Column(name = "regnal_year_to", columnDefinition = "smallint")
  @Convert(converter = YearAttributeConverter.class)
  private Year regnalYearTo;

  @OneToMany(mappedBy = "king", fetch = FetchType.LAZY)
  @Exclude
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
