package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
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
  @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
  public String get() {
    logger.info("Incoming request for '/settings' with method: GET");
    return "settings";
  }
}
