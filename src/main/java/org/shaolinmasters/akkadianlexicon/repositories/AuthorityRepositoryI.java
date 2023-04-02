package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.Authority;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepositoryI extends JpaRepository<Authority, Long> {

  Optional<Authority> findByRole(Role role);
}
