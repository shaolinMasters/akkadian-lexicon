package org.shaolinmasters.akkadianlexicon.repositories;

import org.shaolinmasters.akkadianlexicon.models.WebContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<WebContents, Long> {

  @Query("FROM web_contents w WHERE w.title LIKE %:title%")
  List<WebContents> findByTitle(@Param("title") String title);
}
