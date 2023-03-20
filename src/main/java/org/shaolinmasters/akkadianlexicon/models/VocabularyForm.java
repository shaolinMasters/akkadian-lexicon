package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class VocabularyForm {

  @Column(nullable = false)
  private String nominative;

  @Column(name = "sumerian_form")
  private String sumerianForm;

  @Column(nullable = false)
  private String meaning;
}
