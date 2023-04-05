package org.shaolinmasters.akkadianlexicon.listeners;

import jakarta.annotation.PostConstruct;

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

  @Value("${registration.tokenExpirationDayCount}")
  private Integer expirationDayCount;

  @Value("${registration.confirmationUrlPath}")
  private String emailConfirmationUrlPath;

  @Value("${domain}")
  private String domain;

  private String url;

  @Value("${server.port}")
  private int port;

  @PostConstruct
  private void setUrl() {
    this.url = domain + ":" + port + emailConfirmationUrlPath;
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
    String emailStarterText = webContentService.findByTitle("EMAIL_TEXT_START").getContent();
    String emailText = webContentService.findByTitle("EMAIL_TEXT").getContent();
    String emailActivationText =
      webContentService.findByTitle("EMAIL_ACTIVATION_TEXT").getContent();


    String completeEmailText = "<!DOCTYPE html>"+
      "<html lang=\"en\">"+
      "<head>"+
      "    <meta charset=\"UTF-8\">"+
      "    <title>"+emailSubject+"</title>"+
      "    <head>"+
      "        <style>"+
      "            body {"+
      "                background-color: #ffffff;"+
      "            }"+
      "            a {"+
      "                text-decoration: none;"+
      "            }"+
      ""+
      "            h1 {"+
      "                font-family: 'times new roman', times, baskerville, georgia, serif;"+
      "                color: #92755b;"+
      "                text-align: center;"+
      "                font-size: 32px;"+
      "            }"+
      ""+
      "            h2 {"+
      "                font-family: 'times new roman', times, baskerville, georgia, serif;"+
      "                color: #92755b;"+
      "                font-size: 26px;"+
      "                text-align: center;"+
      "            }"+
      ""+
      "            hr {"+
      "                width: 95%;"+
      "                margin-left: auto;"+
      "                margin-right: auto;"+
      "                color: #666666;"+
      "                text-align: center;"+
      "            }"+
      ""+
      "            p {"+
      "                font-family: 'times new roman', times, baskerville, georgia, serif;"+
      "                color: #000000;"+
      "                font-size: 18px;"+
      "                text-align: center;"+
      "            }"+
      ""+
      "            button {"+
      "                height: 35px;"+
      "                border: 1px solid;"+
      "                background: #92755b;"+
      "                border-radius: 25px;"+
      "                font-size: 16px;"+
      "                color: #ffffff;"+
      "                font-weight: 200;"+
      "                cursor: pointer;"+
      "                outline: none;"+
      "                display: block;"+
      "                margin: 0 auto;"+
      "            }"+
      ""+
      "            button:hover {"+
      "                border-color: #d2a882;"+
      "                transition: .5s;"+
      "                text-align: center;"+
      "            }"+
      ""+
      "            #p1 {"+
      "                font-family: 'times new roman', times, baskerville, georgia, serif;"+
      "                color: #000000;"+
      "                font-size: 18px;"+
      "                text-align: left;"+
      "                margin-left: 20px;"+
      "            }"+
      ""+
      "            #p2 {"+
      "                font-family: 'times new roman', times, baskerville, georgia, serif;"+
      "                color: #000000;"+
      "                font-size: 18px;"+
      "                text-align: left;"+
      "                font-weight: bold;"+
      "                margin-left: 20px;"+
      "            }"+
      ""+
      "        </style>"+
      "    </head>"+
      "<body>"+
      "<h1>Akkadian Lexicon</h1>"+
      "<hr>"+
      "<h2>Welcome!</h2>"+
      "<p>"+emailStarterText+emailAddress+"</p>"+
      "<p>"+emailText+"</p>"+
      "<button>" +
      "<a style=\"color: white;\" href=\"http://"
      + confirmationUrl
      + "\" >"
      + "Confirm"
      + "</a>" +
      "</button>"+
      "<p id=\"p1\" >Thank you,"+
      "</p>"+
      "<p id=\"p2\" >"+
      "    Akkadian Lexicon"+
      "</p>"+
      "<hr>"+
      "</body>"+
      "</html>";



    emailService.sendEmail(emailAddress, emailSubject, String.valueOf(completeEmailText));
    logger.info("Confirmation email has been sent to: " + emailAddress);
  }

  private String createConfirmationUrlWithRegistrationToken(RegistrationToken registrationToken) {
    return url + "?token=" + registrationToken.getTokenString();
  }

  private RegistrationToken createRegistrationTokenForUser(User user) {
    String tokenString = UUID.randomUUID().toString();
    return new RegistrationToken(tokenString, user, calculateExpiryDate(expirationDayCount));
  }

  private LocalDateTime calculateExpiryDate(Integer days) {
    return LocalDateTime.now().plus(days, ChronoUnit.DAYS);
  }
}
