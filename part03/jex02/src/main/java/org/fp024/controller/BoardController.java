package org.fp024.controller;

import org.fp024.domain.BoardVO;
import org.fp024.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @GetMapping("/register")
  public void register() {}

  @PostMapping("/register")
  public String register(BoardVO board, RedirectAttributes rttr) {
    LOGGER.info("register: {}", board);

    service.register(board);

    rttr.addFlashAttribute("result", board.getBno());

    // Spring MVC가 내부적으로 response.sendRedirect()처리를 함
    return "redirect:/board/list";
  }

  @GetMapping({"/get", "/modify"})
  public void get(@RequestParam("bno") Long bno, Model model) {
    LOGGER.info(".get");
    model.addAttribute("board", service.get(bno));
  }

  @PostMapping("/modify")
  public String modify(BoardVO board, RedirectAttributes rttr) {
    LOGGER.info("modify: {}", board);

    if (service.modify(board)) {
      rttr.addFlashAttribute("result", "success");
    }

    return "redirect:/board/list";
  }

  @PostMapping("/remove")
  public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
    LOGGER.info("remove... {}", bno);
    if (service.remove(bno)) {
      rttr.addFlashAttribute("result", "success");
    }

    return "redirect:/board/list";
  }
}