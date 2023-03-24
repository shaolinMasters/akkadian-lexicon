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
    logger.info(
      "Adding modelattribute(named: content): " + aContent + "to view: home");
    model.addAttribute("content", aContent);
    return "home";
  }

  // ezt a metodust kesobb biztos, hogy at kell alakitani, mert nagyon csunya
  @GetMapping("/search")
  public String getSearchPage(
    @ModelAttribute("searchObject") SearchObjectDTO searchObjectDTO, Model model) {
    logger.info("incoming request for /search with request param: " + searchObjectDTO);
    String option = searchObjectDTO.getOption();
    if (option != null) {
      switch (option) {
        case "word" -> processSearchWordQuery(searchObjectDTO, model);
        case "source" -> {
          //search?option=source&king=
          if (searchObjectDTO.getKing() != null) {
            processSearchSourceByKingQuery(searchObjectDTO,model);
          }
          //search?option=source&title=
          else if (searchObjectDTO.getTitle() != null){
            processSearchSourceByTitleQuery(searchObjectDTO,model);
          }
          //search?option=source
          else{
              //source radio button should be checked
            logger.info(
              "Adding modelattribute(named: isSource): " + true + "to view: search");
            model.addAttribute("isSource", true);
          }
        }
        //king radio button should be checked
        case "king" -> {
          logger.info(
            "Adding modelattribute(named: isKing): " + true + "to view: search");
          model.addAttribute("isKing", true);
        }
      }
    }
    List<King> kings = kingService.findAllKings();
    logger.info("Adding modelattribute(named: kings): " + kings + "to view: search");
    model.addAttribute("kings", kings);

    List<Source> sourceList = sourceService.listAllSourcesByTitleAsc();
    logger.info("Adding modelattribute(named: sources): " + sourceList + "to view: search");
    model.addAttribute("sourceList", sourceList);
    return "search";
  }

  @GetMapping("/about")
  public String getAboutPage(Model model) {
    logger.info("Incoming request for '/about'");
    WebContent aContent;
    try {
      aContent = contentService.findByTitle("about_text");
    } catch (ResourceNotFoundException exception) {
      logger.error(exception.getMessage());
      return "about";
    }
    logger.info(
      "Adding modelattribute(named: content): " + aContent + "to view: about");
    model.addAttribute("content", aContent);
    return "about";
  }

  private void processSearchWordQuery(SearchObjectDTO searchObjectDTO, Model model) {
    String word = searchObjectDTO.getWord();
    //search?option=word
    if(word == null){
      //word radio button should be checked
      logger.info(
        "Adding modelattribute(named: isWord): " + true + "to view: search");
      model.addAttribute("isWord", true);
    }
    //search?option=word&word=something
    else if (!"".equals(word)) {
      List<Word> result = wordService.findWordsByNominative(word);
      logger.info("Adding modelattribute(named: words): " + result + "to view: search");
      model.addAttribute("words", result);
      if (result.isEmpty()) {
        model.addAttribute("error", "No "+ word +" word in the database.");
      }
    }
    //search?option=word&word=
    else {
      //word radio button should be checked
      logger.info(
        "Adding modelattribute(named: isWord): " + true + "to view: search");
      model.addAttribute("isWord", true);
    }
  }

  private void processSearchSourceByKingQuery(SearchObjectDTO searchObjectDTO, Model model) {
    String kingName = searchObjectDTO.getKing();
    //search?option=source&king=something
    if (!"".equals(kingName)) {
      try{
        List<Source> sources = sourceService.findSourcesByKingName(kingName);
        logger.info("Adding modelattribute(named: sources): " + sources + "to view: search");
        model.addAttribute("sources", sources);
        if (sources.isEmpty()) {
          String errorMessage = "No sources belong to king: " + kingName + " ";
          model.addAttribute("error", errorMessage);
          logger.info(
            "Adding modelattribute(named: error): " + errorMessage + "to view: search");
        }
      }
      catch (ResourceNotFoundException exception){
        String errorMessage = exception.getMessage();
        logger.info(
          "Adding modelattribute(named: error): " + errorMessage + "to view: search");
        model.addAttribute("error", errorMessage);
      }
    }
    //search?option=source&king=
    else {
      //source radio button should be checked
      logger.info(
        "Adding modelattribute(named: isSrouce): " + true + "to view: search");
      model.addAttribute("isSource", true);
    }
  }

  private void processSearchSourceByTitleQuery(SearchObjectDTO searchObjectDTO, Model model) {
    String sourceTitle = searchObjectDTO.getTitle();
    //search?option=source&title=something
    if (!"".equals(sourceTitle)) {
      Source source;
      try {
        source = sourceService.findSourceByTitle(sourceTitle);
        logger.info("Adding modelattribute(named: source): " + source + "to view: search");
        model.addAttribute("source", source);
      }
      catch (ResourceNotFoundException exception){
        String errorMessage = "No sources found with title: " + sourceTitle;
        model.addAttribute("error", errorMessage);
        logger.info(
          "Adding modelattribute(named: error): " + errorMessage + "to view: search");
      }
    }
    //search?option=source&title=
    else {
      //source radio button should be checked
      logger.info(
        "Adding modelattribute(named: isSource): " + true + "to view: search");
      model.addAttribute("isSource", true);
    }
  }
}
