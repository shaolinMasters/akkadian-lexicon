package org.shaolinmasters.akkadianlexicon.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.Authority;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.shaolinmasters.akkadianlexicon.repositories.AuthorityRepositoryI;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorityService {

  private final AuthorityRepositoryI authorityRepository;

  public Authority findByRole(Role role) {
    Optional<Authority> result = authorityRepository.findByRole(role);
    if (result.isPresent()) {
      return result.get();
    } else throw new ResourceNotFoundException("Not found authority with role: " + role);
  }
}
