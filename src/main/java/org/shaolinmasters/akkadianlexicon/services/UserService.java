package org.shaolinmasters.akkadianlexicon.services;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.dtos.UserDTO;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.exceptions.UserAlreadyExistException;
import org.shaolinmasters.akkadianlexicon.models.Authority;
import org.shaolinmasters.akkadianlexicon.models.RegistrationToken;
import org.shaolinmasters.akkadianlexicon.models.SecurityUser;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.shaolinmasters.akkadianlexicon.repositories.RegistrationRepositoryI;
import org.shaolinmasters.akkadianlexicon.repositories.UserRepositoryI;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepositoryI userRepository;
  private final RegistrationRepositoryI registrationRepository;
  private final AuthorityService authorityService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> result = userRepository.findByEmailIgnoreCase(email);
    if (result.isPresent()) {
      return new SecurityUser(result.get());
    }
    throw new UsernameNotFoundException("User with email: " + email + " not found.");
  }

  public User registerNewAccount(UserDTO userDto, Role role) {
    User userToRegister = new User();
    Set<Authority> authorities = new HashSet<>();
    authorities.add(authorityService.findByRole(role));
    userToRegister.setAuthorities(authorities);
    userToRegister.setEmail(userDto.getEmail());
    try {
      return userRepository.save(userToRegister);
    }
    catch (Exception e){
      logger.error(e.getMessage());
      throw new UserAlreadyExistException("user already exists");
    }
  }

  public RegistrationToken createRegistrationToken(User user, String token) {
//        User user = findUserByEmail(createUserDTO.getEmail());
    RegistrationToken registrationToken = new RegistrationToken(token, user);
    return registrationRepository.save(registrationToken);
  }


  @Transactional
  public void confirmRegistration(String token
//  , Locale locale, String appUrl
  ) {
    //atirni, hogy tokenservicet hasznaljon
    Optional<RegistrationToken> registrationToken = registrationRepository.findByToken(token);
    if (registrationToken.isPresent()) {
      RegistrationToken regToken = registrationToken.get();
      User user = regToken.getUser();
//      if(user.getStatus().equals(Status.APPROVED)){
//        throw new RegistrationHasAlreadyBeenConfirmedException("Registration has already been confirmed");
//      }
//      else{
//        CreateUserDTO createUserDTO = modelMapper.map(user, CreateUserDTO.class);
//        if(regToken.getExpiryDate().isBefore(LocalDateTime.now())){
//          sendRegistrationConfirmationEmail(user, locale, appUrl);
//          throw new ExpiredRegistrationTokenException("Registration token is expired");
//        }
//        setUserStatus(Status.APPROVED, user);
//        return true;
//      }
      user.setEnabled(true);
      userRepository.save(user);
    } else {
      throw new ResourceNotFoundException("Registration token is not valid");
    }
  }
}
