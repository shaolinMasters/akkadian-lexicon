package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.List;
import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface SourceRepositoryI extends JpaRepository<Source, Long> {

  @Query("select s from Source s where s.title = ?1")
  Optional<Source> findByTitle(String title);

  @Query("select s from Source s order by s.title")
  List<Source> findAllOrderByTitleAsc();

  @Query("select s from Source s where s.king = ?1")
  List<Source> findByKing(@Nullable King king);

  @Query("select s from Source s where s.king.id is null order by s.title")
  List<Source> findAllByKingIdNullOrderByTitleAsc();

  @Query("select s from Source s where s.king = ?1 order by s.title")
  List<Source> findAllByKingOrderByTitleAsc(King king);
}
