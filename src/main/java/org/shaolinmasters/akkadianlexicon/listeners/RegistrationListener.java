package org.shaolinmasters.akkadianlexicon.listeners;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Transient;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.events.OnRegistrationCompleteEvent;
import org.shaolinmasters.akkadianlexicon.models.RegistrationToken;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.services.EmailService;
import org.shaolinmasters.akkadianlexicon.services.RegistrationTokenService;
import org.shaolinmasters.akkadianlexicon.services.UserService;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  private final WebContentService webContentService;
  private final EmailService emailService;
  private final RegistrationTokenService registrationTokenService;

  @Value( "${registration.tokenExpirationDayCount}" )
  private Integer expirationDayCount;

  @Value("${registration.confirmationUrlPath}")
  private String emailConfirmationUrlPath;

  private String url;

  @Value("${server.port}")
  private int port;

  @PostConstruct
  private void setUrl() throws UnknownHostException {
    this.url = InetAddress.getLocalHost().getHostAddress() + ":" + port + emailConfirmationUrlPath;
  }

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
      User user = onRegistrationCompleteEvent.getUser();
      RegistrationToken registrationToken = createRegistrationTokenForUser(user);
      registrationTokenService.saveRegistrationToken(registrationToken);
      String confirmationUrl = createConfirmationUrlWithRegistrationToken(registrationToken);
      sendRegistrationConfirmationEmailToUser(user, confirmationUrl);
  }

  private void sendRegistrationConfirmationEmailToUser(User user, String confirmationUrl) {
    String emailAddress = user.getEmail();
    String emailSubject = webContentService.findByTitle("EMAIL_SUBJECT").getContent();
    String emailText = webContentService.findByTitle("EMAIL_TEXT").getContent();
    String emailActivationText = webContentService.findByTitle("EMAIL_ACTIVATION_TEXT").getContent();

    emailText = emailText + "<br/>" + "<a href=\"http://" + confirmationUrl + "\" >" + emailActivationText + "</a>";
    emailText = emailText + "<br/>" + "<p>Your username: " + emailAddress + "</p>";
    emailService.sendEmail(emailAddress, emailSubject, emailText);
    logger.info("Confirmation email has been sent to: " + emailAddress);
  }

  private String createConfirmationUrlWithRegistrationToken(RegistrationToken registrationToken) {
    return  url + "?token=" + registrationToken.getTokenString();
  }

  private RegistrationToken createRegistrationTokenForUser(User user) {
    String tokenString = UUID.randomUUID().toString();
    return new RegistrationToken(tokenString, user, calculateExpiryDate(expirationDayCount));
  }

  private LocalDateTime calculateExpiryDate(Integer days) {
    return LocalDateTime.now().plus(days, ChronoUnit.DAYS);
  }

}
