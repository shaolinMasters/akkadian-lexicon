package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsController {

  private final UserService userService;

  @PostMapping("/delete/user")
  public String deleteUser(@RequestParam Long id, Model m) {
    userService.deleteUser(id);
    //m.addAttribute("isUser", true);
    m.addAttribute("isDelete", true);
    return "redirect:/settings?action=delete";
  }
}
