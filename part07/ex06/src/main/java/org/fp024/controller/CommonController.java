package org.fp024.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CommonController {

  @GetMapping("/accessError")
  public void accessDenied(Authentication auth, Model model) {
    LOGGER.info("access Denied: {}", auth);
    model.addAttribute("msg", "Access Denied");
  }
}
