package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegistrationTokenRepositoryI extends JpaRepository<RegistrationToken,Long> {

  @Query("select r from RegistrationToken r where r.tokenString = ?1")
  Optional<RegistrationToken> findByToken(String token);

}
