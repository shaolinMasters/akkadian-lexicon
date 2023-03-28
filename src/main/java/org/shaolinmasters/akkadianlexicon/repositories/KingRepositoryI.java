package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.List;
import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KingRepositoryI extends JpaRepository<King, Long> {
  @Query("select k from King k where upper(k.name) = upper(?1)")
  Optional<King> findByNameIgnoreCase(String name);


  @Query("select k from King k order by k.regnalYearFrom, k.name")
  List<King> findAllOrderByRegnalYearFromAscNameAsc();

}
