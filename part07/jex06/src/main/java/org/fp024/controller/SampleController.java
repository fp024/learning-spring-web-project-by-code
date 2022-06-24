package org.fp024.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/sample/*")
@Controller
public class SampleController {
  @GetMapping("/all")
  public void doAll() {
    LOGGER.info("do all can access everybody");
  }

  @GetMapping("/member")
  public void doMember() {
    LOGGER.info("login member");
  }

  @GetMapping("/admin")
  public void doAdmin() {
    LOGGER.info("admin only");
  }
}
