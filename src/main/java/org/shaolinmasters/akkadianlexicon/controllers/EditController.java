package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.dtos.SourceDTO;
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

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/edit")
public class EditController {

  private final Logger logger = LoggerFactory.getLogger(EditController.class);

  private final KingService kingService;

  private final SourceService sourceService;

  @GetMapping
  public String get(Model m) {
    logger.info("Incoming request for '/edit' with method: GET");
    m.addAttribute("newSource", new SourceDTO());
    m.addAttribute("kings", kingService.findAllKings());
    m.addAttribute("isSource", false);
    return "edit";
  }

  @PostMapping("/new/source")
  public String saveSource(@ModelAttribute("newSource") @Validated SourceDTO source, BindingResult bindingResult, Model model) {
    logger.info("Incoming request for '/new/source' with method: POST");
    if (bindingResult.hasErrors()) {
      model.addAttribute("isSource", true);
      return "/edit";
    }
    sourceService.saveSource(source);
    model.addAttribute("isSource", true);
    model.addAttribute("isNew", true);
    return "edit";
  }
}
