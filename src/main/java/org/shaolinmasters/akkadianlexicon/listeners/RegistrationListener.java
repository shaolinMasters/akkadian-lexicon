package org.shaolinmasters.akkadianlexicon.listeners;

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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

//  @Value( "${email.confirmationUrlPath}" )
  private String emailConfirmationUrlPath;

//  @Value( "${server.port}" )
  private Integer port;

  private final Environment environment;

  private final WebContentService webContentService;
  private final EmailService emailService;
  private final UserService userService;


  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
    try {
      confirmRegistration(onRegistrationCompleteEvent);
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) throws UnknownHostException {
    User user = event.getUser();
    String token = UUID.randomUUID().toString();
    userService.createRegistrationToken(user, token);
    String emailAddress = user.getEmail();
    emailConfirmationUrlPath = environment.getProperty("confirmationUrlPath" );
//    port=Integer.parseInt(environment.getProperty("server.port"));
    port= 8080;
    String confirmationUrl = InetAddress.getLocalHost().getHostAddress() + ":" + port + emailConfirmationUrlPath + "?token=" + token;
    logger.info("a teljes confirmationurl: " + confirmationUrl);

    String emailSubject = webContentService.findByTitle("EMAIL_SUBJECT").getContent();
    String emailText = webContentService.findByTitle("EMAIL_TEXT").getContent();
    String emailActivationText = webContentService.findByTitle("EMAIL_ACTIVATION_TEXT").getContent();

    emailText = (emailText + "<br/>"
      + "<a href='" + confirmationUrl + "'>" + emailActivationText + "</a>"
      + "<p style=\"display:none;\">This paragraph should be hidden.</p>\n");
    emailService.sendEmail(emailAddress, emailSubject, emailText);

    logger.info("Confirmation email has been sent to: " + emailAddress);
  }


}
