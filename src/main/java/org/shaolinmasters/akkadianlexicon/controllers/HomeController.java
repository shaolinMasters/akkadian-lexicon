package org.shaolinmasters.akkadianlexicon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping(path = {"/"})
  String getWelcome() {
    return "home";
  }
}
