package org.fp024.controller;

import org.fp024.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
  
  private BoardService service;
    
  @GetMapping("/list")
  public void list(Model model) {
    LOGGER.info("list");   
    model.addAttribute("list", service.getList());
  }
}
