package org.shaolinmasters.akkadianlexicon.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.dtos.KingDTO;
import org.shaolinmasters.akkadianlexicon.dtos.NotVerbDTO;
import org.shaolinmasters.akkadianlexicon.dtos.SourceDTO;
import org.shaolinmasters.akkadianlexicon.dtos.VerbDTO;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/edit")
public class EditController {

  private final Logger logger = LoggerFactory.getLogger(EditController.class);

  private final KingService kingService;

  private final SourceService sourceService;

  private final WordService wordService;

  @GetMapping
  public String get(Model m) {
    logger.info("Incoming request for '/edit' with method: GET");
    addModelsToEditPage(m);

    return "edit";
  }

  @PostMapping("/new/king")
  public RedirectView saveKing(
    @ModelAttribute("newKing") @Validated KingDTO king,
    BindingResult bindingResult,
    Model model,
    RedirectAttributes attributes) {
    logger.info("Incoming request for '/new/king' with method: POST");
    if (bindingResult.hasErrors()) {
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.newKing", bindingResult);
      attributes.addFlashAttribute("newKing", king);
      return new RedirectView("/edit?option=king&action=create&error");
    }
    //model.addAttribute("sourceHasErrors", false);
    //model.addAttribute("kingHasErrors", false);
    //model.addAttribute("isKing", true);
    //model.addAttribute("isSource", false);
    //logger.info(String.valueOf(king));
    kingService.saveKing(king);
    addModelsToEditPage(model);

    return new RedirectView("/edit?option=king&action=create");
  }

  @GetMapping(params = {"option=king", "action=create"})
  public String getCreateKing(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isKing", true);
    m.addAttribute("isCreate", true);
    return "edit";
  }

  @PostMapping("/new/source")
  public RedirectView saveSource(
    @ModelAttribute("newSource") @Validated SourceDTO source,
    BindingResult bindingResult,
    RedirectAttributes attributes) {
    if (bindingResult.hasErrors()) {
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.newSource", bindingResult);
      attributes.addFlashAttribute("newSource", source);
      return new RedirectView("/edit?option=source&action=create&error");
    }


    sourceService.saveSource(source);
    addModelsToEditPage(model);
    return new RedirectView("/edit?option=source&action=create");
  }

  @GetMapping(params = {"option=source", "action=create"})
  public String getCreateSource(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isSource", true);
    m.addAttribute("isCreate", true);
    return "edit";
  }

  @PostMapping("/delete/source")
  public String deleteSource(@RequestParam Long id, Model m) {
    sourceService.deleteSourceById(id);
    return "redirect:/edit?option=source&action=delete";
  }

  @GetMapping(params = {"option=king", "action=create", "error"})
  public String getCreateKingError(Model m) {
    System.out.println("meghivodtam");
    if (m.containsAttribute("newKing") && m.containsAttribute("org.springframework.validation.BindingResult.newKing")) {
      System.out.println("meghivodtam megint");
      Object kdto = m.getAttribute("newKing");
      Object error = m.getAttribute("org.springframework.validation.BindingResult.newKing");
      addModelsToEditPage(m);
      m.addAttribute("newKing", kdto);
      m.addAttribute("org.springframework.validation.BindingResult.newKing", error);
      m.addAttribute("kingHasErrors", true);
      m.addAttribute("isKing", true);
      m.addAttribute("isCreate", true);
      return "edit";
    }
    return "redirect:/edit";
  }


  @GetMapping(params = {"option=source", "action=delete"})
  public String getDeleteSource(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isSource", true);
    m.addAttribute("isDelete", true);
    m.addAttribute("id", 0);
    return "edit";
  }





  @PostMapping("/new/verb")
  public String saveVerb(
    @ModelAttribute  VerbDTO verb,
    Model model
  ) {
    logger.info("Incoming request for '/new/source' with method: POST");
    model.addAttribute("isWord", true);
    model.addAttribute("sourceHasErrors", false);
    model.addAttribute("kingHasErrors", false);
    model.addAttribute("wordHasErrors", false);
    wordService.saveVerb(verb);
    addModelsToEditPage(model);
    logger.info(String.valueOf(verb));

    return "/edit";
  }

  @PostMapping("/new/not-verb")
  public String saveNotVerb(
    @ModelAttribute  NotVerbDTO notVerb,
    Model model
  ) {
    logger.info("Incoming request for '/new/source' with method: POST");
    model.addAttribute("isWord", true);
    String wordClass = notVerb.getWordClass();
    switch (wordClass) {
      case "noun" -> wordService.saveNoun(notVerb);
      case "adjective" -> wordService.saveAdjective(notVerb);
      case "pronoun" -> wordService.savePronoun(notVerb);
      case "adverb" -> wordService.saveAdverb(notVerb);
    }
    model.addAttribute("sourceHasErrors", false);
    model.addAttribute("kingHasErrors", false);
    model.addAttribute("wordHasErrors", false);
    addModelsToEditPage(model);
    logger.info(String.valueOf(notVerb));

    return "edit";
  }



  @PostMapping("/delete/king")
  public String deleteKing(@RequestParam Long id, Model m) {
    m.addAttribute("isKing", true);
    m.addAttribute("isDelete", true);
    kingService.deleteKingById(id);
    return "edit";
  }


  public void addModelsToEditPage(Model model) {
    model.addAttribute("newSource", new SourceDTO());
    model.addAttribute("kings", kingService.findAllKingsOrderByRegnalYearFromAscNameAsc());
    model.addAttribute("sources", sourceService.listAllSourcesByTitleAsc());
    model.addAttribute("newKing", new KingDTO());
    model.addAttribute("sourcesWithoutKing", sourceService.listAllSourcesWithoutKingByTitleAsc());
    model.addAttribute("sourceHasErrors", false);
    model.addAttribute("kingHasErrors", false);
    model.addAttribute("isCreate", false);
    model.addAttribute("isDelete", false);
    model.addAttribute("isSource", false);
    model.addAttribute("isKing", false);
    model.addAttribute("isWord", false);
  }


  @PostMapping("/delete/king")
  public String deleteKing(@RequestParam Long id, Model m) {
    m.addAttribute("isKing", true);
    m.addAttribute("isDelete", true);
    kingService.deleteKingById(id);
    return "redirect:/edit?option=king&action=delete";
  }

  @GetMapping(params = {"option=king", "action=delete"})
  public String getDeleteKing(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isKing", true);
    m.addAttribute("isDelete", true);
    m.addAttribute("id", 0);
    return "edit";
  }

  @GetMapping(params = {"option=king"})
  public String getKing(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isKing", true);
    return "edit";
  }

  @GetMapping(params = {"option=word"})
  public String getWord(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isWord", true);
    return "edit";
  }

  @GetMapping(params = {"option=source"})
  public String getSource(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isSource", true);
    return "edit";
  }

  @GetMapping(params = {"option=source", "action=create", "error"})
  public String getCreateSourceError(Model m) {
    if (m.containsAttribute("newSource") && m.containsAttribute("org.springframework.validation.BindingResult.newSource")) {
      Object sdto = m.getAttribute("newSource");
      Object error = m.getAttribute("org.springframework.validation.BindingResult.newSource");
      addModelsToEditPage(m);
      m.addAttribute("newSource", sdto);
      m.addAttribute("org.springframework.validation.BindingResult.newSource", error);
      m.addAttribute("sourceHasErrors", true);
      m.addAttribute("isSource", true);
      m.addAttribute("isCreate", true);
      return "edit";
    }
    return "redirect:/edit";
  }

}
