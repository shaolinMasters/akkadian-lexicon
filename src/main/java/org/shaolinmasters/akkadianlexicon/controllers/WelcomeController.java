package org.shaolinmasters.akkadianlexicon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
  @GetMapping(path = {"/"})
  String getWelcome() {
    return "welcome";
  }
}
