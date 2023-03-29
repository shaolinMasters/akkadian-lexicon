package org.shaolinmasters.akkadianlexicon.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.controllers.SearchController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private final SearchController searchController;

  @ResponseStatus
  @ExceptionHandler(Exception.class)
  public String handleException( HttpServletRequest request,Exception ex){
    String requestURI = request.getRequestURI();
    logger.error("Requested URI: " + requestURI);
    logger.error("Exception Raised: " + ex);
    return "edit";
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public String handleResourceNotFoundException(
      HttpServletRequest request, Exception ex, Model model) {
    String requestURI = request.getRequestURI();
    logger.error("Requested URI: " + requestURI);
    logger.error("Exception Raised: " + ex);

    switch (requestURI) {
      case "/" -> {
        return "home";
      }
      case "/about" -> {
        return "about";
      }
      case "/bibliography" -> {
        return "bibliography";
      }
      case "/search" -> {
        if (request.getQueryString().contains("option=source")) {
          logger.info("Adding modelattribute(named: isSource): " + true + "to view: search");
          model.addAttribute("isSource", true);
        } else if (request.getQueryString().contains("option=king")) {
          logger.info("Adding modelattribute(named: isKing): " + true + "to view: search");
          model.addAttribute("isKing", true);
        } else if (request.getQueryString().contains("option=word")) {
          logger.info("Adding modelattribute(named: isWord): " + true + "to view: search");
          model.addAttribute("isWord", true);
        }
        String errorMessage = ex.getMessage();
        logger.info("Adding modelattribute(named: error): " + errorMessage + "to view: search");
        model.addAttribute("error", errorMessage);
        searchController.addKingsAndSourcesToModel(model);
        return "search";
      }
      default -> {
        model.addAttribute("error", 404);
        logger.info("Adding modelattribute(named: error): " + 404 + "to view: error");
        return "error";
      }
    }
  }
}
