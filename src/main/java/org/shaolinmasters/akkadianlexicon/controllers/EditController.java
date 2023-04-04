package org.shaolinmasters.akkadianlexicon.controllers;

import java.util.EnumSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.dtos.KingDTO;
import org.shaolinmasters.akkadianlexicon.dtos.NotVerbDTO;
import org.shaolinmasters.akkadianlexicon.dtos.SourceDTO;
import org.shaolinmasters.akkadianlexicon.dtos.VerbDTO;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.shaolinmasters.akkadianlexicon.models.enums.VerbalStem;
import org.shaolinmasters.akkadianlexicon.models.enums.VowelClass;
import org.shaolinmasters.akkadianlexicon.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EditController {


  private final KingService kingService;

  private final SourceService sourceService;

  private final WordService wordService;

  private final UserService userService;

  @GetMapping
  public String get(Model m) {
    logger.info("Incoming request for '/edit' with method: GET");
    addModelsToEditPage(m);

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

  @GetMapping(params = {"option=source", "action=create"})
  public String getCreateSource(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isSource", true);
    m.addAttribute("isCreate", true);
    return "edit";
  }

  @GetMapping(params = {"option=source", "action=delete"})
  public String getDeleteSource(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isSource", true);
    m.addAttribute("isDelete", true);
    m.addAttribute("id", 0);
    return "edit";
  }

  @GetMapping(params = {"option=king", "action=create"})
  public String getCreateKing(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isKing", true);
    m.addAttribute("isCreate", true);
    return "edit";
  }

  @GetMapping(params = {"option=king", "action=delete"})
  public String getDeleteKing(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isKing", true);
    m.addAttribute("isDelete", true);
    m.addAttribute("id", 0);
    return "edit";
  }

  @GetMapping(params = {"option=word", "action=create"})
  public String getCreateWord(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isWord", true);
    m.addAttribute("isCreate", true);
    return "edit";
  }

  @GetMapping(params = {"option=word", "action=delete"})
  public String getDeleteWord(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isWord", true);
    m.addAttribute("isDelete", true);
    m.addAttribute("id", 0);
    return "edit";
  }

  @GetMapping(params = {"option=word", "word"})
  public String getSearchResult(@RequestParam(required = false) String word, Model m) {
    if (word != null && !"".equals(word)) {
      List<Word> result = wordService.findWordsByNominative(word);
      logger.info("Adding modelattribute(named: words): " + result + "to view: search");
      m.addAttribute("words", result);
      if (result.isEmpty()) {
        String errorMessage = "No " + word + " word in the database.";
        logger.info("Adding modelattribute(named: error): " + errorMessage + "to view: search");
        m.addAttribute("error", errorMessage);
      }
    }
    addModelsToEditPage(m);
    m.addAttribute("isWord", true);
    m.addAttribute("isDelete", true);
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


  @PostMapping("/new/source")
  public RedirectView saveSource(
    @ModelAttribute("newSource") @Validated SourceDTO source,
    BindingResult bindingResult,
    Model model,
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

  @PostMapping("/delete/source")
  public String deleteSource(@RequestParam Long id, Model m) {
    sourceService.deleteSourceById(id);
    return "redirect:/edit?option=source&action=delete";
  }



  @PostMapping("/delete/king")
  public String deleteKing(@RequestParam Long id, Model m) {
    m.addAttribute("isKing", true);
    m.addAttribute("isDelete", true);
    kingService.deleteKingById(id);
    return "redirect:/edit?option=king&action=delete";
  }

  @PostMapping("/delete/word")
  public String deleteWord(@RequestParam Long id, Model m) {
    m.addAttribute("isWord", true);
    m.addAttribute("isDelete", true);
    wordService.deleteWordById(id);
    return "redirect:/edit?option=word&action=delete";
  }



  @GetMapping(params = {"option=king", "action=create", "error"})
  public String getCreateKingError(Model m) {
    if (m.containsAttribute("newKing") && m.containsAttribute("org.springframework.validation.BindingResult.newKing")) {
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


  @PostMapping("/new/verb")
  public RedirectView saveVerb(
    @ModelAttribute @Validated VerbDTO verb,
    BindingResult bindingResult,
    Model model,
    RedirectAttributes attributes
  ) {
    if (bindingResult.hasErrors()) {
//      bindingResult.getAllErrors().stream().forEach(System.out::println);
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.newVerb", bindingResult);
      attributes.addFlashAttribute("newVerb", verb);
      return new RedirectView("/edit?option=word&action=create&wordclass=verb&error");
    }

    wordService.saveVerb(verb);
    addModelsToEditPage(model);
    return new RedirectView("/edit?option=word&action=create");
  }

  @PostMapping("/new/not-verb")
  public RedirectView saveNotVerb(
    @ModelAttribute @Validated NotVerbDTO notVerb,
    BindingResult bindingResult,
    Model model,
    RedirectAttributes attributes
  ) {
    if (bindingResult.hasErrors()) {
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.newNotVerb", bindingResult);
      attributes.addFlashAttribute("newNotVerb", notVerb);
      return new RedirectView("/edit?option=word&action=create&wordclass=notverb&error");
    }
    String wordClass = notVerb.getWordClass();
    switch (wordClass) {
      case "Noun" -> wordService.saveNoun(notVerb);
      case "Adjective" -> wordService.saveAdjective(notVerb);
      case "Pronoun" -> wordService.savePronoun(notVerb);
      case "Adverb" -> wordService.saveAdverb(notVerb);
    }
    model.addAttribute("sourceHasErrors", false);
    model.addAttribute("kingHasErrors", false);
    model.addAttribute("wordHasErrors", false);
    addModelsToEditPage(model);
    return new RedirectView("/edit?option=word&action=create");
  }

  @GetMapping(params = {"option=word", "action=create", "wordclass=verb","error"})
  public String getCreateVerbError(Model m) {
    logger.info("getCreateVerb");
    if (m.containsAttribute("newVerb") && m.containsAttribute("org.springframework.validation.BindingResult.newVerb")) {
      Object verbdto = m.getAttribute("newVerb");
      Object error = m.getAttribute("org.springframework.validation.BindingResult.newVerb");
      addModelsToEditPage(m);
      m.addAttribute("newVerb", verbdto);
      m.addAttribute("org.springframework.validation.BindingResult.newVerb", error);
      m.addAttribute("verbHasErrors", true);
      m.addAttribute("isWord", true);
      m.addAttribute("isCreate", true);
      return "edit";
    }
    return "redirect:/edit";
  }


  @GetMapping(params = {"option=word", "action=create", "wordclass=notverb","error"})
  public String getCreateNotVerbError(Model m) {
    logger.info("getCreateNotVerbErroban vagyok");
    if (m.containsAttribute("newNotVerb") && m.containsAttribute("org.springframework.validation.BindingResult.newNotVerb")) {
      Object notVerbdto = m.getAttribute("newNotVerb");
      Object error = m.getAttribute("org.springframework.validation.BindingResult.newNotVerb");
      addModelsToEditPage(m);
      m.addAttribute("newNotVerb", notVerbdto);
      m.addAttribute("org.springframework.validation.BindingResult.newNotVerb", error);
      m.addAttribute("notVerbHasErrors", true);
      m.addAttribute("isWord", true);
      m.addAttribute("isCreate", true);
      return "edit";
    }
    return "redirect:/edit";
  }








  public void addModelsToEditPage(Model model) {
    model.addAttribute("newSource", new SourceDTO());
    model.addAttribute("kings", kingService.findAllKingsOrderByRegnalYearFromAscNameAsc());
    model.addAttribute("sources", sourceService.listAllSourcesByTitleAsc());
    model.addAttribute("newKing", new KingDTO());
    model.addAttribute("sourcesWithoutKing", sourceService.listAllSourcesWithoutKingByTitleAsc());
    model.addAttribute("sourceHasErrors", false);
    model.addAttribute("kingHasErrors", false);
    model.addAttribute("verbHasErrors", false);
    model.addAttribute("notVerbHasErrors", false);
    model.addAttribute("isCreate", false);
    model.addAttribute("isDelete", false);
    model.addAttribute("isSource", false);
    model.addAttribute("isKing", false);
    model.addAttribute("isWord", false);
    model.addAttribute("verbalStems", EnumSet.allOf(VerbalStem.class));
    model.addAttribute("vowelClasses", EnumSet.allOf(VowelClass.class));
    model.addAttribute("newVerb", new VerbDTO());
    model.addAttribute("newNotVerb", new NotVerbDTO());
  }










}
