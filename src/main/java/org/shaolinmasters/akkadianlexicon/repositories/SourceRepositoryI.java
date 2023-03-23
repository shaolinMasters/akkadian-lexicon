package org.shaolinmasters.akkadianlexicon.repositories;

import org.shaolinmasters.akkadianlexicon.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SourceRepositoryI extends JpaRepository<Source, Long> {

  @Query("SELECT s FROM Source s WHERE s.title LIKE %?1%")
  Optional<Source> findByTitle(String keyword);

  @Query("select s.title from Source s order by s.title")
  List<String> findByOrderByTitleAsc();

  @Query("SELECT s FROM Source s WHERE s.catalogueRef LIKE %?1%")

  List<Source> findAllByCatalogueRef(String keyword);
}
