package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.services.SourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.shaolinmasters.akkadianlexicon.dtos.SearchObjectDTO;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;
import org.shaolinmasters.akkadianlexicon.services.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final Logger logger = LoggerFactory.getLogger(HomeController.class);

  private final WordService wordService;

  private final WebContentService contentService;

  private final SourceService sourceService;

  @GetMapping("/")
  public String getHomePage(Model model) {
    logger.info("Incoming request for '/'");
    WebContent aContent;
    try {
      aContent = contentService.findByTitle("home_text");
    } catch (ResourceNotFoundException exception) {
      logger.error(exception.getMessage());
      return "home";
    }
    model.addAttribute("content", aContent);
    return "home";
  }

  @GetMapping("/search")
  public String getSearchResult(
    @ModelAttribute("searchObject") SearchObjectDTO searchObjectDTO, Model model) {
    logger.info("incoming request for /search with request param: " + searchObjectDTO);
    String option = searchObjectDTO.getOption();
    if (option != null) {
      switch (option) {
        case "word" -> {
          if (!"".equals(searchObjectDTO.getWord())) {
            List<Word> result = wordService.findWordsByNominative(searchObjectDTO.getWord());
            logger.info("Adding modelattribute(named: words): " + result + "to view: search");
            model.addAttribute("words", result);
            if (result.isEmpty()) {
              model.addAttribute("error", "No such word in the database.");
            }
          } else {
            model.addAttribute("words", List.of());
          }
        }
        case "source" -> {
          Source result = sourceService.findSourceByTitle(searchObjectDTO.getSourceTitle());
          model.addAttribute("source", result);
          logger.info("Adding modelAttribute(named: source): " + result + "to view: search");
        }
      }
    }
    model.addAttribute("sourceList", sourceService.listAllSourcesByTitle());
    return "search";
  }




}






