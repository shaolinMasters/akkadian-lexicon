package org.shaolinmasters.akkadianlexicon.services;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.RegistrationToken;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.repositories.RegistrationTokenRepositoryI;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationTokenService {

  private final RegistrationTokenRepositoryI registrationTokenRepository;

  @Transactional
  public RegistrationToken saveRegistrationToken(RegistrationToken registrationToken) {
    return registrationTokenRepository.save(registrationToken);
  }

  public boolean isValidToken(String tokenString) {
    RegistrationToken registrationToken = findByTokenString(tokenString);
    if (registrationToken.getExpiryDateTime().isBefore(LocalDateTime.now())) {
      return false;
    }
    return true;
  }

  public RegistrationToken findByTokenString(String tokenString) {
    Optional<RegistrationToken> optionalRegistrationToken =
        registrationTokenRepository.findByToken(tokenString);
    if (optionalRegistrationToken.isPresent()) {
      return optionalRegistrationToken.get();
    }
    throw new ResourceNotFoundException("Token: " + tokenString + " is not found");
  }

  public void deleteToken(RegistrationToken registrationToken) {
    registrationTokenRepository.delete(registrationToken);
  }

  public void deleteTokenByDeletedUser(User user) {
    registrationTokenRepository.deleteByUser(user);
  }
}
