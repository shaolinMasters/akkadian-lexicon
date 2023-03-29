package org.shaolinmasters.akkadianlexicon.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.SecurityUser;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.repositories.UserRepositoryI;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepositoryI userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> result = userRepository.findByEmailIgnoreCase(email);
    if (result.isPresent()) {
      return new SecurityUser(result.get());
    }
    throw new UsernameNotFoundException("User with email: " + email + " not found.");
  }



}
