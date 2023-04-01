package org.shaolinmasters.akkadianlexicon.services;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
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
public class EmailService {

private final Environment env;

  private final JavaMailSender javaMailSender;
  private final Logger logger = LoggerFactory.getLogger(EmailService.class);

//  public void sendEmail(String emailAddress, String emailSubject, String emailText){
//
//    MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
//      mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(emailAddress));
//      mimeMessage.setSubject(emailSubject);
//      mimeMessage.setText(emailText,"UTF-8","html");
//    };
//    javaMailSender.send(mimeMessagePreparator);
//  }
  @Async
  public void sendEmail(String emailAddress, String title, String mailMessage){
    logger.info("sending email start");
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(env.getProperty("spring.mail.username"));
    message.setTo(emailAddress);
    message.setSubject(title);
    message.setText(mailMessage);
    javaMailSender.send(message);
    logger.info("sending email end");

  }
}
