package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.RegistrationToken;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationTokenRepositoryI extends JpaRepository<RegistrationToken, Long> {
  @Transactional
  @Modifying
  @Query("delete from RegistrationToken r where r.user = ?1")
  void deleteByUser(User user);

  @Query("select r from RegistrationToken r where r.tokenString = ?1")
  Optional<RegistrationToken> findByToken(String token);
}
