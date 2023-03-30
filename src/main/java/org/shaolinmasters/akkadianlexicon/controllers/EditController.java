package org.shaolinmasters.akkadianlexicon.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.dtos.SourceDTO;
import org.shaolinmasters.akkadianlexicon.models.King;
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
    addModelsToEditPage(m);
    m.addAttribute("isSource", false);
    return "edit";
  }

  @PostMapping("/new/source")
  public String saveSource(@ModelAttribute("newSource") @Validated SourceDTO source, BindingResult bindingResult, Model model) {
    logger.info("Incoming request for '/new/source' with method: POST");
    model.addAttribute("isSource", true);
    if (bindingResult.hasErrors()) {
      logger.info("Adding modelattribute(named: newSource): " + source + "to view: edit");
      model.addAttribute("newSource", source);
      List<King> kings = kingService.findAllKings();
      logger.info("Adding modelattribute(named: kings): " + kings + "to view: edit");
      model.addAttribute("kings", kings);
      return "/edit";
    }
    sourceService.saveSource(source);
    addModelsToEditPage(model);
    return "edit";
  }


  @PostMapping("/delete/source")
  public String deleteSource(@RequestParam Long id, Model m) {
    m.addAttribute("isSource", true);
    m.addAttribute("isDelete", true);
    sourceService.deleteSourceById(id);
    return "edit";
  }


  public void addModelsToEditPage(Model model){
    SourceDTO sourceDTO = new SourceDTO();
    logger.info("Adding modelattribute(named: newSource): " + sourceDTO);
    model.addAttribute("newSource", sourceDTO);
    List<King> kings = kingService.findAllKings();
    logger.info("Adding modelattribute(named: kings): " + kings);
    model.addAttribute("kings", kings);
  }
}
