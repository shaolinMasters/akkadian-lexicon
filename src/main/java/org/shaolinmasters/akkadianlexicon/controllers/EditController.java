package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.dtos.KingDTO;
import org.shaolinmasters.akkadianlexicon.dtos.SourceDTO;
import org.shaolinmasters.akkadianlexicon.services.KingService;
import org.shaolinmasters.akkadianlexicon.services.SourceService;
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

  @GetMapping
  public String get(Model m) {
    addModelsToEditPage(m);
    return "edit";
  }


  @PostMapping("/new/king")
  public String saveKing(
    @ModelAttribute("newKing") @Validated KingDTO king,
    BindingResult bindingResult,
    Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("kingHasErrors", true);
      model.addAttribute("sourceHasErrors", false);
      model.addAttribute("newSource", new SourceDTO());
      model.addAttribute("sourcesWithoutKing", sourceService.listAllSourcesWithoutKingByTitleAsc());
      model.addAttribute("kings", kingService.findAllKingsOrderByRegnalYearFromAscNameAsc());
      return "edit";
    }
    model.addAttribute("sourceHasErrors", false);
    model.addAttribute("kingHasErrors", false);
    model.addAttribute("isKing", true);
    model.addAttribute("isSource", false);
    kingService.saveKing(king);
    addModelsToEditPage(model);
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
    return new RedirectView("/edit?option=source&action=create");
  }

  @GetMapping(params = {"option=source", "action=create"})
  public String getCreateSource(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isSource", true);
    m.addAttribute("isCreate", true);
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


  @PostMapping("/delete/source")
  public String deleteSource(@RequestParam Long id, Model m) {
    sourceService.deleteSourceById(id);
    return "redirect:/edit?option=source&action=delete";
  }

  @GetMapping(params = {"option=source", "action=delete"})
  public String getDeleteSource(Model m) {
    addModelsToEditPage(m);
    m.addAttribute("isSource", true);
    m.addAttribute("isDelete", true);
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
}
