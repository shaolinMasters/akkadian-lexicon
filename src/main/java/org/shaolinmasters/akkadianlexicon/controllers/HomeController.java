package org.shaolinmasters.akkadianlexicon.controllers;

import org.shaolinmasters.akkadianlexicon.models.WebContents;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.shaolinmasters.akkadianlexicon.services.HomeService;

@RequiredArgsConstructor
@Controller
public class HomeController {

  private final HomeService contentService;

  @GetMapping("/")
  public String viewContent(Model model) {
    String title = "home_text";
    WebContents aContent = contentService.findByTitle(title);
    model.addAttribute("content", aContent);
    return "home";
  }
}
