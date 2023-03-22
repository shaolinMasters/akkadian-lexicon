package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WebContentRepositoryI extends JpaRepository<WebContent, Long> {

  @Query("select w from WebContent w where w.title = ?1")
  Optional<WebContent> findByTitle(String title);
}
