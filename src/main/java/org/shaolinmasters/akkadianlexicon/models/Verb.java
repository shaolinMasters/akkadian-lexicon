package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.shaolinmasters.akkadianlexicon.models.enums.VerbalStem;
import org.shaolinmasters.akkadianlexicon.models.enums.VowelClass;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Verb extends Word {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VowelClass vowelClass;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VerbalStem verbalStem;

  public void setVerbalStem (String verbalStem) {
    this.verbalStem = VerbalStem.valueOf(verbalStem);
  }

  public void setVowelClass (String vowelClass) {
    this.vowelClass = VowelClass.valueOf(vowelClass);
  }


}
