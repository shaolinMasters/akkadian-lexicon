package org.shaolinmasters.akkadianlexicon.repositories;

import org.shaolinmasters.akkadianlexicon.models.King;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KingRepositoryI extends JpaRepository <King, Long> {

  @Query("SELECT name FROM King WHERE name = ?1")
  Optional<King> findByName(String name);

}
