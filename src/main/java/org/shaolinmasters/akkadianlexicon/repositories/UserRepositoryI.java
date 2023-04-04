package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.List;
import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepositoryI extends JpaRepository<User, Long> {

  @Query("select u from User u where upper(u.email) = upper(?1)")
  Optional<User> findByEmailIgnoreCase(String email);

}
