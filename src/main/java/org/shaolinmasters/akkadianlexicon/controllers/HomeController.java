package org.shaolinmasters.akkadianlexicon.controllers;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.WebContents;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.shaolinmasters.akkadianlexicon.services.HomeService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class HomeController {

  private final HomeService homeService;

  @GetMapping("/")
  public String viewContent(Model model) {
    Optional <String> title = "home_text".describeConstable();

    //TODO: itt az lehhe a jo, hogy amikor az adatbazisba feltoltjuk a szoveget,
    // akkor ezzel a cimmel vinnenk fel: home_text
    // at is lehet nevezni, csak akkor itt es a html fajlban is at kell majd
    //TODO: tovabbi teendo -> html-ben at kell irni az osztalyt, mert jelenleg hardcodolva adjuk vissza a szoveget

    List<WebContents> aContent = homeService.findByTitle(title);
    model.addAttribute("content", aContent);
    return "home";
  }

  @GetMapping("/search")
  String getSearch() {
    return "search";
  }
}
