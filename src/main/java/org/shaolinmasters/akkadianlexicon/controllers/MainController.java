package org.shaolinmasters.akkadianlexicon.controllers;


import java.util.Calendar;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.*;
import org.shaolinmasters.akkadianlexicon.services.UserService;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final WebContentService contentService;

  private final Logger logger = LoggerFactory.getLogger(MainController.class);

  private final UserService userService;

  @GetMapping("/")
  public String getHomePage(Model model) {
    logger.info("Incoming request for '/' with method: GET");
    WebContent aContent = contentService.findByTitle("home_text");
    logger.info("Adding modelattribute(named: content): " + aContent + "to view: home");
    model.addAttribute("content", aContent);
    return "home";
  }

  @GetMapping("/about")
  public String getAboutPage(Model model) {
    logger.info("Incoming request for '/about' with method: GET");
    WebContent aContent = contentService.findByTitle("about_text");
    logger.info("Adding modelattribute(named: content): " + aContent + "to view: about");
    model.addAttribute("content", aContent);
    return "about";
  }

  @GetMapping("/bibliography")
  public String getBibliographyPage(Model model) {
    logger.info("Incoming request for '/bibliography' with method: GET");
    WebContent aContent = contentService.findByTitle("bibliography_text");
    logger.info("Adding modelattribute(named: content): " + aContent + "to view: bibliography");
    model.addAttribute("content", aContent);
    return "bibliography";
  }


  @GetMapping("/login")
  public String login() {
    logger.info("Incoming request for '/login' with method: GET");
    return "login";
  }

  @GetMapping("/users/registrationConfirm")
  public String confirmRegistration
    (WebRequest request, Model model, @RequestParam("token") String token) {

//    Locale locale = request.getLocale();
//
//    VerificationToken verificationToken = userService.getVerificationToken(token);
//    if (verificationToken == null) {
//      String message = messages.getMessage("auth.message.invalidToken", null, locale);
//      model.addAttribute("message", message);
//      return "redirect:/badUser.html?lang=" + locale.getLanguage();
//    }
//
//    User user = verificationToken.getUser();
//    Calendar cal = Calendar.getInstance();
//    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//      String messageValue = messages.getMessage("auth.message.expired", null, locale)
//      model.addAttribute("message", messageValue);
//      return "redirect:/badUser.html?lang=" + locale.getLanguage();
//    }
//
//    user.setEnabled(true);
    userService.confirmRegistration(token);
    return "redirect:/login";
  }
}
