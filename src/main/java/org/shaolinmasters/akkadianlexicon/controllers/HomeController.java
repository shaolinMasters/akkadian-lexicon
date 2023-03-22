package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final WebContentService contentService;
  private final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @GetMapping("/")
  public String getHomePage(Model model) {
    logger.info("Incoming request for '/'");
    WebContent aContent;
    try {
      aContent = contentService.findByTitle("home_text");
    }
    catch(RuntimeException exception){
      logger.error(exception.getMessage());
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
