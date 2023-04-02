package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.dtos.ConfirmAdminDTO;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.shaolinmasters.akkadianlexicon.services.RegistrationTokenService;
import org.shaolinmasters.akkadianlexicon.services.UserService;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

  private final WebContentService contentService;

  private final UserService userService;

  private final RegistrationTokenService registrationTokenService;

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

  @GetMapping(value = "/register", params = "token")
  public String confirmRegistration(Model model, @RequestParam("token") String tokenString) {
    ConfirmAdminDTO confirmAdminDTO = new ConfirmAdminDTO();
    if (registrationTokenService.isValidToken(tokenString)) {
      model.addAttribute("isValidToken", true);
      confirmAdminDTO.setTokenString(tokenString);
      model.addAttribute("confirmAdmin", confirmAdminDTO);
    } else {
      model.addAttribute("confirmAdmin", confirmAdminDTO);
      model.addAttribute("isValidToken", false);
    }
    return "password";
  }

  @PostMapping("/register")
  public String setPasswordAndConfirm(
      @ModelAttribute("confirmAdmin") ConfirmAdminDTO confirmAdminDTO, Model model) {
    if (registrationTokenService.isValidToken(confirmAdminDTO.getTokenString())) {
      // if passwords are OK
      userService.confirmAdminUser(confirmAdminDTO);
    } else {
      model.addAttribute("confirmAdmin", new ConfirmAdminDTO());
      model.addAttribute("isValidToken", false);
      return "password";
    }
    return "login";
  }
}
