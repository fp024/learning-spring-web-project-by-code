package org.fp024.controller;

import static org.fp024.util.CommonUtil.unixPathToCurrentSystemPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.BoardAttachVO;
import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;
import org.fp024.domain.PageDTO;
import org.fp024.domain.SearchType;
import org.fp024.service.BoardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
@PropertySource("classpath:project-data.properties")
public class BoardController {
  private final BoardService service;

  @Value("${multipart.uploadFolder}")
  private String UPLOAD_FOLDER;

  @GetMapping("/list")
  public String list(Criteria criteria, Model model) {
    LOGGER.info("list");
    List<BoardVO> pagedList = service.getList(criteria);

    // 드문 상황이지만, 한 페이지에서 한 게시물만 삭제된 상태에서, 그 페이지에 그대로 남는 문제가 있어서,
    // 일단은 그럴 경우 직전 페이지로 리다이렉트 되도록 하려다가, 페이지 번호를 임의로 크게 입력했을 떄,
    // 연달아 없는 경우 그만큼 반복적인 리다이렉트가 일어날 문제가 보여 일단은 1페이지로 보내기로 했다.
    if (pagedList.isEmpty() && criteria.getPageNum() > 1) {
      criteria.setPageNum(1);
      return "redirect:/board/list" + criteria.getLink();
    }

    model.addAttribute("list", pagedList);
    PageDTO pageDTO = new PageDTO(criteria, service.getTotal(criteria));
    LOGGER.info("criteria: {}, pageDTO: {}", criteria, pageDTO);
    model.addAttribute("pageMaker", pageDTO);
    model.addAttribute("allSearchTypeSet", SearchType.allSearchTypeSet());

    return "board/list";
  }

  @GetMapping("/register")
  @PreAuthorize("isAuthenticated()")
  public void register() {}

  @PostMapping("/register")
  @PreAuthorize("isAuthenticated()")
  public String register(BoardVO board, RedirectAttributes rttr) {
    LOGGER.info("====================================");
    LOGGER.info("register: {}", board);

    if (board.getAttachList() != null) {
      board.getAttachList().forEach(attach -> LOGGER.info(attach.toString()));
    }
    LOGGER.info("====================================");
    service.register(board);
    rttr.addFlashAttribute("result", board.getBno());
    // Spring MVC가 내부적으로 response.sendRedirect()처리를 함
    return "redirect:/board/list";
  }

  @GetMapping({"/get", "/modify"})
  public void get(
      @RequestParam("bno") Long bno, @ModelAttribute("criteria") Criteria criteria, Model model) {
    LOGGER.info(".get");
    model.addAttribute("board", service.get(bno));
  }

  @PreAuthorize("principal.username == #board.writer")
  @PostMapping("/modify")
  public String modify(
      BoardVO board, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes rttr) {
    LOGGER.info("modify: {}", board);

    if (service.modify(board)) {
      rttr.addFlashAttribute("result", "success");
    }

    return "redirect:/board/list" + criteria.getLink();
  }

  @PreAuthorize("principal.username == #writer")
  @PostMapping("/remove")
  public String remove(
      @RequestParam("bno") Long bno,
      String writer,
      @ModelAttribute("criteria") Criteria criteria,
      RedirectAttributes rttr) {
    LOGGER.info("remove... {}", bno);

    List<BoardAttachVO> attachList = service.getAttachList(bno);

    if (service.remove(bno)) {
      // 첨부 파일 삭제
      deleteFiles(attachList);
      rttr.addFlashAttribute("result", "success");
    }
    return "redirect:/board/list" + criteria.getLink();
  }

  @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
    LOGGER.info("getAttachList: {}", bno);
    return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
  }

  private void deleteFiles(List<BoardAttachVO> attachList) {
    if (attachList == null || attachList.isEmpty()) {
      return;
    }

    LOGGER.info("delete attach files..........");
    LOGGER.info(attachList.toString());

    attachList.forEach(
        attach -> {
          final String uploadPath = unixPathToCurrentSystemPath(attach.getUploadPath());
          try {
            Path file =
                Paths.get(
                    UPLOAD_FOLDER
                        + File.separator
                        + uploadPath
                        + File.separator
                        + attach.getUuid()
                        + "_"
                        + attach.getFileName());
            Files.deleteIfExists(file);

            if (Files.probeContentType(file).startsWith("image")) {
              Path thumbnail =
                  Paths.get(
                      UPLOAD_FOLDER
                          + File.separator
                          + uploadPath
                          + File.separator
                          + "s_"
                          + attach.getUuid()
                          + "_"
                          + attach.getFileName());
              Files.delete(thumbnail);
            }
          } catch (IOException e) {
            LOGGER.error("delete file error {}", e.getMessage(), e);
          }
        });
  }
}
