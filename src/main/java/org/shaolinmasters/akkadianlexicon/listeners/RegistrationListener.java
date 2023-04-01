package org.shaolinmasters.akkadianlexicon.listeners;

import jakarta.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.events.OnRegistrationCompleteEvent;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.services.EmailService;
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
  private final UserService userService;

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
    try {
      confirmRegistration(onRegistrationCompleteEvent);
    } catch (UnknownHostException ignored) {
      logger.error("Could not get host address.");
    }
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) throws UnknownHostException {
    User user = event.getUser();
    String token = UUID.randomUUID().toString();
    userService.createRegistrationToken(user, token);
    String emailAddress = user.getEmail();
    String confirmationUrl = url + "?token=" + token;

    String emailSubject = webContentService.findByTitle("EMAIL_SUBJECT").getContent();
    String emailText = webContentService.findByTitle("EMAIL_TEXT").getContent();
    String emailActivationText = webContentService.findByTitle("EMAIL_ACTIVATION_TEXT").getContent();
    logger.info(confirmationUrl);

    emailText = emailText + "<br/>" + "<a href=\"http://" + confirmationUrl + "\" >" + emailActivationText + "</a>";
    emailService.sendEmail(emailAddress, emailSubject, emailText);
    logger.info("Confirmation email has been sent to: " + emailAddress);
  }

}
