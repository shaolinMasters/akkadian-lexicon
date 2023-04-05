package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.List;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WordRepositoryI extends JpaRepository<Word, Long> {

  @Query(
      """
    select w from Word w
    where upper(w.vocabularyForm.nominative) like upper(concat('%', :nominative, '%'))
    order by w.vocabularyForm.nominative""")
  List<Word> findAllByNominativeContainsIgnoreCaseOrderByNominativeAsc(String nominative);
}
