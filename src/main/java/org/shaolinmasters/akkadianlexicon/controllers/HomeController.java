package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.dtos.SearchObject;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.shaolinmasters.akkadianlexicon.services.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final WordService wordService;

  private final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @GetMapping("/")
  String getWelcome() {
    return "welcome";
  }


  @GetMapping("/search")
  String getSearch() {
    return "search";
  }

  @GetMapping("/search/")
  public String getWord(@ModelAttribute("search")SearchObject searchObject, Model model) {
    logger.info("incoming request for /search with request param: " + searchObject);
    Word result;
    try {
      result = wordService.loadWordByNominative(searchObject.getWord().toString());
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
