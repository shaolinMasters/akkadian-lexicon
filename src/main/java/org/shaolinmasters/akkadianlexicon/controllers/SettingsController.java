package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/settings")
public class SettingsController {

  private final Logger logger = LoggerFactory.getLogger(EditController.class);

  @GetMapping
  public String get(Model m) {
    logger.info("Incoming request for '/settings' with method: GET");
    return "settings";
  }
}
