package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.List;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SourceRepositoryI extends JpaRepository<Source, Long> {

  List<Source> findByKingNameIgnoreCaseOrderByTitleAsc(String name);

  @Query("select s from Source s where s.title = ?1")
  Source findByTitle(String title);

  @Query("select s from Source s order by s.title")
  List<Source> findByOrderByTitleAsc();
}
