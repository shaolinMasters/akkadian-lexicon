package org.shaolinmasters.akkadianlexicon.controllers;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

  private final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    logger.error("Requested URI: " + request.getRequestURI());
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());
      model.addAttribute("error", statusCode);
    }
    return "error";
  }
}
