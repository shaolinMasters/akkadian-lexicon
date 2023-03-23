package org.shaolinmasters.akkadianlexicon.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.dtos.SearchObjectDTO;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.shaolinmasters.akkadianlexicon.services.KingService;
import org.shaolinmasters.akkadianlexicon.services.SourceService;
import org.shaolinmasters.akkadianlexicon.services.WebContentService;
import org.shaolinmasters.akkadianlexicon.services.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final WordService wordService;

  private final WebContentService contentService;

  private final KingService kingService;

  private final SourceService sourceService;

  private final Logger logger = LoggerFactory.getLogger(MainController.class);

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

  // ezt a metodust kesobb biztos, hogy at kell alakitani, mert nagyon csunya
  @GetMapping("/search")
  public String getSearch(
    @ModelAttribute("searchObject") SearchObjectDTO searchObjectDTO, Model model) {
    logger.info("incoming request for /search with request param: " + searchObjectDTO);
    String option = searchObjectDTO.getOption();
    if (option != null) {
      switch (option) {
        case "word" -> processSearchWordQuery(searchObjectDTO, model);
        case "source" -> processSearchSourceByKingQuery(searchObjectDTO, model);
      }
    }
    List<King> kings = kingService.findAllKings();
    logger.info("Adding modelattribute(named: kings): " + kings + "to view: search");
    model.addAttribute("kings", kings);
    return "search";
  }

  @GetMapping("/about")
  public String getAboutPage(Model model) {
    logger.info("Incoming request for '/about'");
    WebContent aContent;
    try {
      aContent = contentService.findByTitle("about_text");
    } catch (RuntimeException exception) {
      logger.error(exception.getMessage());
      return "about";
    }
    model.addAttribute("content", aContent);
    return "about";
  }

  private void processSearchWordQuery(SearchObjectDTO searchObjectDTO, Model model) {
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

  private void processSearchSourceByKingQuery(SearchObjectDTO searchObjectDTO, Model model) {
    String kingName = searchObjectDTO.getKing();
    if (!"".equals(kingName)) {
      List<Source> sources = sourceService.findSourcesByKingName(kingName);
      logger.info("Adding modelattribute(named: sources): " + sources + "to view: search");
      model.addAttribute("sources", sources);
      if (sources.isEmpty()) {
        String errorMessage = "No sources belong to king: " + kingName + " ";
        model.addAttribute("error", errorMessage);
        logger.info(
          "Adding modelattribute(named: error): " + errorMessage + "to view: search");
      }
    } else {
      logger.info("Adding modelattribute(named: sources): " + List.of() + "to view: search");
      model.addAttribute("sources", List.of());
    }
  }
}
