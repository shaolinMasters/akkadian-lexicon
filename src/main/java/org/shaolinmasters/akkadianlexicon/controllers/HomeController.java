package org.shaolinmasters.akkadianlexicon.controllers;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {

  private final WebContentService webContentService;
  private final Logger logger = (Logger) LoggerFactory.getLogger(HomeController.class);

  // TODO: itt az lehhe a jo, hogy amikor az adatbazisba feltoltjuk a szoveget,
  // akkor ezzel a cimmel vinnenk fel: home_text
  // at is lehet nevezni, csak akkor itt es a html fajlban is at kell majd
  // TODO: tovabbi teendo -> html-ben at kell irni az osztalyt, mert jelenleg hardcodolva adjuk
  // vissza a szoveget

  @GetMapping("/")
  public String getHomePage(Model model) {
    logger.info("Incoming request for '/'");
    WebContent aContent;
    try {
      aContent = webContentService.getWebContent("home_text");
    } catch (RuntimeException runtimeException) {
      logger.error(runtimeException.getMessage());
      return "home";
    }
    model.addAttribute("content", aContent);
    return "home";
  }

  @GetMapping("/search")
  String getSearch() {
    return "search";
  }
}
