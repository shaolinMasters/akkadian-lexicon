package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.dtos.AdminDTO;
import org.shaolinmasters.akkadianlexicon.events.OnRegistrationCompleteEvent;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.shaolinmasters.akkadianlexicon.services.AuthorityService;
import org.shaolinmasters.akkadianlexicon.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/settings")
@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
public class SettingsController {

  private final ApplicationEventPublisher eventPublisher;
  private final UserService userService;

  @GetMapping
  public String get(Model model) {
    addModelsToSettingsPage(model);
    return "settings";
  }

  @GetMapping(
      value = "/user",
      params = {"option=admin", "action=create"})
  public String getCreateAdmin(Model model) {
    addModelsToSettingsPage(model);
    model.addAttribute("isCreate", true);
    model.addAttribute("isAdmin", true);
    return "settings";
  }

  @PostMapping(
      value = "/user",
      params = {"option=admin", "action=create"})
  public RedirectView createAdmin(
    @ModelAttribute("newAdmin") @Validated AdminDTO adminDto,
    BindingResult bindingResult,
    Model model,
    RedirectAttributes attributes
    ) {
    if(bindingResult.hasErrors()) {
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.newAdmin", bindingResult);
      attributes.addFlashAttribute("newAdmin", adminDto);
      return new RedirectView("/settings/user?option=admin&action=create&error");
    }
    User registeredUser = userService.createAccountWithRole(adminDto, Role.ROLE_ADMIN);
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser));
    addModelsToSettingsPage(model);
    model.addAttribute("isCreate", true);
    model.addAttribute("isAdmin", true);
    return new RedirectView("/settings");
  }
  @GetMapping(value = "user",  params = {"option=admin", "action=create", "error"})
  public String getCreateAdminError(Model m) {
    if (m.containsAttribute("newAdmin") && m.containsAttribute("org.springframework.validation.BindingResult.newAdmin")) {
      Object adto = m.getAttribute("newAdmin");
      Object error = m.getAttribute("org.springframework.validation.BindingResult.newAdmin");
      addModelsToSettingsPage(m);
      m.addAttribute("newAdmin", adto);
      m.addAttribute("org.springframework.validation.BindingResult.newAdmin", error);
      m.addAttribute("adminHasErrors", true);
      m.addAttribute("isAdmin", true);
      m.addAttribute("isCreate", true);
      return "settings";
    }
    return "redirect:/settings";
  }

  @GetMapping(
    value = "/user",
    params= {"option=admin", "action=delete"})
  public String getDeleteAdmin(Model m) {
    addModelsToSettingsPage(m);
    m.addAttribute("isDelete", true);
    m.addAttribute("id", 0);
    return "settings";
  }
  @PostMapping("/delete/user")
  public String deleteUser(@RequestParam Long id, Model m) {
    userService.deleteUserById(id);
    m.addAttribute("isDelete", true);
    return "redirect:/settings?action=delete";
  }

  public void addModelsToSettingsPage(Model model) {
    model.addAttribute("newAdmin", new AdminDTO());
    model.addAttribute("adminHasErrors", false);
    model.addAttribute("isCreate", false);
    model.addAttribute("isDelete", false);
    model.addAttribute("isAdmin", false);
    List<User> adminList = userService.listAllAdmin();
    model.addAttribute("adminList", adminList);
  }
}
