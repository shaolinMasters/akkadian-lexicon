package org.shaolinmasters.akkadianlexicon.services;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

  private final JavaMailSender javaMailSender;

  @Async
  public void sendEmail(String emailAddress, String emailSubject, String emailText){

    MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
      mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(emailAddress));
      mimeMessage.setSubject(emailSubject);
      mimeMessage.setText(emailText,"UTF-8","html");
      logger.info(mimeMessage.getContent().toString());
    };
    javaMailSender.send(mimeMessagePreparator);
  }
}
