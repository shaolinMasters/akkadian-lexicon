package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public abstract class Word {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded private VocabularyForm vocabularyForm;

  @ManyToMany(mappedBy = "words")
  @Exclude
  List<Source> sources;
}
