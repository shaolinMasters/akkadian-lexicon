package org.shaolinmasters.akkadianlexicon.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.shaolinmasters.akkadianlexicon.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/search")
public class SearchController {

  private final WordService wordService;

  private final KingService kingService;

  private final SourceService sourceService;
  private final Logger logger = LoggerFactory.getLogger(SearchController.class);

  @GetMapping
  public String get(Model model) {
    addKingsAndSourcesToModel(model);
    return "search";
  }

  @GetMapping(params = "option=word")
  public String getWord(Model model, @RequestParam(required = false) String word) {
    model.addAttribute("isWord", true);
    if (word != null && !"".equals(word)) {
      List<Word> result = wordService.findWordsByNominative(word);
      logger.info("Adding modelattribute(named: words): " + result + "to view: search");
      model.addAttribute("words", result);
      if (result.isEmpty()) {
        String errorMessage = "No " + word + " word in the database.";
        logger.info("Adding modelattribute(named: error): " + errorMessage + "to view: search");
        model.addAttribute("error", errorMessage);
      }
    }
    addKingsAndSourcesToModel(model);
    return "search";
  }

  @GetMapping(params = "option=king")
  public String getSourceByKing(Model model, @RequestParam(required = false) String name) {
    logger.info("Adding modelattribute(named: isKing): " + true + "to view: search");
    model.addAttribute("isKing", true);
    if (name != null && !"".equals(name)) {
      List<Source> sources = sourceService.findSourcesByKingName(name);
      logger.info("Adding modelattribute(named: sources): " + sources + "to view: search");
      model.addAttribute("sources", sources);
      if (sources.isEmpty()) {
        String errorMessage = "No sources belong to king: " + name + " ";
        model.addAttribute("error", errorMessage);
        logger.info("Adding modelattribute(named: error): " + errorMessage + "to view: search");
      }
    }
    addKingsAndSourcesToModel(model);
    return "search";
  }

  @GetMapping(params = "option=source")
  String getSourceByTitle(Model model, @RequestParam(required = false) String title) {
    logger.info("Adding modelattribute(named: isSource): " + true + "to view: search");
    model.addAttribute("isSource", true);
    if (title != null && !"".equals(title)) {
      Source source = sourceService.findSourceByTitle(title);
      logger.info("Adding modelattribute(named: source): " + source + "to view: search");
      model.addAttribute("source", source);
    }
    addKingsAndSourcesToModel(model);
    return "search";
  }

  public void addKingsAndSourcesToModel(Model model) {
    List<King> kings = kingService.findAllKingsOrderByRegnalYearFromAscNameAsc();
    logger.info("Adding modelattribute(named: kings): " + kings + "to view: search");
    model.addAttribute("kings", kings);

    List<Source> sourceList = sourceService.listAllSourcesByTitleAsc();
    logger.info("Adding modelattribute(named: sources): " + sourceList + "to view: search");
    model.addAttribute("sourceList", sourceList);
  }
}
