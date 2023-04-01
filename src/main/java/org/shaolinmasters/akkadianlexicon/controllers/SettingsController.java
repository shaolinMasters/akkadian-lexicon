package org.shaolinmasters.akkadianlexicon.controllers;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.dtos.UserDTO;
import org.shaolinmasters.akkadianlexicon.events.OnRegistrationCompleteEvent;
import org.shaolinmasters.akkadianlexicon.exceptions.UserAlreadyExistException;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.shaolinmasters.akkadianlexicon.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/settings")
@RequiredArgsConstructor
@Slf4j
public class SettingsController {

  private final ApplicationEventPublisher eventPublisher;
  private final UserService userService;

  @GetMapping("/user/create")
  public String registerAccount(
//    @ModelAttribute("user") UserDTO userDto,
    HttpServletRequest request
//    , Errors errors
  ){
    UserDTO userDto = new UserDTO();
    userDto.setEmail("marincsakt@gmail.com");
    User registeredUser;
    try {
      registeredUser = userService.registerNewAccount(userDto, Role.ROLE_ADMIN);
    } catch (UserAlreadyExistException uaeEx) {
      logger.error(uaeEx.getMessage());
      return "home";
    } catch (RuntimeException ex) {
      logger.error(ex.getMessage());
      return "home";
    }
    String appUrl = request.getContextPath();
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, appUrl));
    return "home";
  }

}
