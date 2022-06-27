package org.fp024.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.fp024.type.MemberAuthType.RoleNames.ROLE_ADMIN;
import static org.fp024.type.MemberAuthType.RoleNames.ROLE_MEMBER;

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

  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "', '" + ROLE_MEMBER + "')")
  @GetMapping("/annoMember")
  public void doMember2() {
    LOGGER.info("logined annotation member");
  }

  @Secured({ROLE_ADMIN})
  @GetMapping("/annoAdmin")
  public void doAdmin2() {
    LOGGER.info("admin annotation only");
  }
}