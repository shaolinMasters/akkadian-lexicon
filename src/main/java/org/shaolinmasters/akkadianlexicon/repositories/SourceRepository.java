package org.shaolinmasters.akkadianlexicon.repositories;

import org.shaolinmasters.akkadianlexicon.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Long> {

  @Query("SELECT s FROM Source s WHERE s.title LIKE %?1%"
        + " OR s.catalogueRef LIKE %?1%")

  List<Source> search(String keyword);
}
