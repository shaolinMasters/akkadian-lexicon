package org.shaolinmasters.akkadianlexicon.controllers;

import java.util.EnumSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.dtos.AdminDTO;
import org.shaolinmasters.akkadianlexicon.dtos.KingDTO;
import org.shaolinmasters.akkadianlexicon.dtos.NotVerbDTO;
import org.shaolinmasters.akkadianlexicon.dtos.SourceDTO;
import org.shaolinmasters.akkadianlexicon.dtos.VerbDTO;
import org.shaolinmasters.akkadianlexicon.events.OnRegistrationCompleteEvent;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.shaolinmasters.akkadianlexicon.models.enums.VerbalStem;
import org.shaolinmasters.akkadianlexicon.models.enums.VowelClass;
import org.shaolinmasters.akkadianlexicon.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsController {

  private final ApplicationEventPublisher eventPublisher;
  private final UserService userService;


  @GetMapping
  public String get(Model model){
    addModelsToSettingsPage(model);
    return "settings";
  }

  @GetMapping(value = "/user", params = {"option=admin", "action=create"})
  public String getCreateAdmin(Model model){
    addModelsToSettingsPage(model);
    model.addAttribute("isCreate", true);
    model.addAttribute("isAdmin", true);
    return "settings";
  }

  @PostMapping(value = "/user", params = {"option=admin", "action=create"})
  public String createAdmin(
    @ModelAttribute("newAdmin") AdminDTO adminDto, Model model
  ) {
    User registeredUser = userService.createAccountWithRole(adminDto, Role.ROLE_ADMIN);
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser));
    addModelsToSettingsPage(model);
    model.addAttribute("isCreate", true);
    model.addAttribute("isAdmin", true);
    return "settings";
  }

  public void addModelsToSettingsPage(Model model) {
    model.addAttribute("newAdmin", new AdminDTO());
    model.addAttribute("adminHasErrors", false);
    model.addAttribute("isCreate", false);
    model.addAttribute("isDelete", false);
    model.addAttribute("isAdmin", false);
  }

}
