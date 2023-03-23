package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.List;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepositoryI extends JpaRepository<Source, Long> {

  List<Source> findByKingNameIgnoreCaseOrderByTitleAsc(String name);
}
