package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;
import org.shaolinmasters.akkadianlexicon.dtos.UserDTO;
import org.shaolinmasters.akkadianlexicon.models.*;
import org.shaolinmasters.akkadianlexicon.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/admin")
  public String getAdmin() {
    return "admin";
  }

  @GetMapping("/login")
  public String login(Model model) {
    logger.info("Incoming request for '/login' with method: GET");
    model.addAttribute("userDTO", new UserDTO());
    return "login";
  }

  @PostMapping("/login-user")
  public String loginUser(@ModelAttribute UserDTO user, Model m) {
    User adminUSer = (User) userService.loadUserByUsername(user.getEmail());
    m.addAttribute("user", adminUSer);
    return "home";
  }

}








