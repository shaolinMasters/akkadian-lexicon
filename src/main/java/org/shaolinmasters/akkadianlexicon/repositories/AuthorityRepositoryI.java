package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.Authority;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorityRepositoryI extends JpaRepository<Authority, Long> {

  Optional<Authority> findByRole(Role role);
}
