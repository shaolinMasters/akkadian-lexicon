package org.shaolinmasters.akkadianlexicon.repositories;

import jakarta.transaction.Transactional;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WordRepositoryI extends JpaRepository<Word, Long> {

  @Transactional
  @Query("SELECT w FROM Word w where nominative like ?1")
  Optional<Word> getWordByNominative(String word);
}
