package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;

import org.shaolinmasters.akkadianlexicon.dtos.SearchObject;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.shaolinmasters.akkadianlexicon.services.WordService;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class HomeController {


  private final WordService wordService;

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

  @GetMapping("/search/")
  public String getWord(@ModelAttribute("searchObject") SearchObject searchObject, Model model) {
    logger.info("incoming request for /search with request param: " + searchObject);
    Word result;
    try {
      result = wordService.loadWordByNominative(searchObject.getWord());
    } catch (ResourceNotFoundException e) {
      logger.error(e.getMessage());

      String error = "nem letezik ilyen szo az adatbazisban";
      model.addAttribute("error", error);
      return "search";
    }
    model.addAttribute("word", result.getVocabularyForm());


    return "search";
  }

}
