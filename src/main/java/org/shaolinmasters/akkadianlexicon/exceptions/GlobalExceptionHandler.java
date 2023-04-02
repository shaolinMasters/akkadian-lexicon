package org.shaolinmasters.akkadianlexicon.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.controllers.SearchController;
import org.shaolinmasters.akkadianlexicon.dtos.AdminDTO;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

  private final SearchController searchController;

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public String handleException(HttpServletRequest request, Exception ex) {
    String requestURI = request.getRequestURI();
    logger.error("Requested URI: " + requestURI);
    logger.error("Exception Raised: " + ex);
    return "error";
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

  @ResponseStatus(value = HttpStatus.IM_USED)
  @ExceptionHandler(UserAlreadyExistException.class)
  public String handleUserAlreadyExistException(Exception ex, Model model) {
    model.addAttribute("newAdmin", new AdminDTO());
    model.addAttribute("adminHasErrors", false);
    model.addAttribute("isDelete", false);
    model.addAttribute("isCreate", true);
    model.addAttribute("isAdmin", true);
    model.addAttribute("error", ex.getMessage());
    return "settings";
  }


}
