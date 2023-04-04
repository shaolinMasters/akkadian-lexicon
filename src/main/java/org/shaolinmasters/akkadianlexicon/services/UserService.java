package org.shaolinmasters.akkadianlexicon.services;

import java.util.Optional;
import java.util.Set;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.dtos.AdminDTO;
import org.shaolinmasters.akkadianlexicon.dtos.ConfirmAdminDTO;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.exceptions.UserAlreadyExistException;
import org.shaolinmasters.akkadianlexicon.models.Authority;
import org.shaolinmasters.akkadianlexicon.models.RegistrationToken;
import org.shaolinmasters.akkadianlexicon.models.SecurityUser;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.shaolinmasters.akkadianlexicon.repositories.UserRepositoryI;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepositoryI userRepository;
  private final AuthorityService authorityService;
  private final RegistrationTokenService registrationTokenService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> result = userRepository.findByEmailIgnoreCase(email);
    if (result.isPresent()) {
      return new SecurityUser(result.get());
    }
    throw new UsernameNotFoundException("User with email: " + email + " not found.");
  }

  public User createAccountWithRole(AdminDTO adminDto, Role role) {
    String email = adminDto.getEmail();
    try {
      findUserByEmail(email);
      throw new UserAlreadyExistException("User with email: " + email + " already exists.");
    } catch (ResourceNotFoundException exception) {
      User userToRegister = new User();
      Set<Authority> authorities = new HashSet<>();
      authorities.add(authorityService.findByRole(role));
      userToRegister.setAuthorities(authorities);
      userToRegister.setEmail(email);
      userToRegister.setDegree(adminDto.getDegree());
      userToRegister.setInstitution(adminDto.getInstitution());
      userToRegister.setName(adminDto.getName());
      return userRepository.save(userToRegister);
    }
  }

  public User findUserByEmail(String email) {
    Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    }
    throw new ResourceNotFoundException("User with email: " + email + " not found.");
  }

  @Transactional
  public void confirmAdminUser(ConfirmAdminDTO confirmAdminDTO) {
    RegistrationToken registrationToken =
        registrationTokenService.findByTokenString(confirmAdminDTO.getTokenString());
    User user = registrationToken.getUser();
    user.setEnabled(true);
    user.setPassword(passwordEncoder.encode(confirmAdminDTO.getPassword()));
    userRepository.save(user);
    registrationTokenService.deleteToken(registrationToken);
  }

  @Transactional
  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }

  public List<User> listAllAdmin () {
    return userRepository.findByAuthorities_RoleOrderByNameAsc(Role.ROLE_ADMIN);
  }
}
