package org.fp024.controller;

import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.SampleVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
@Slf4j
public class SampleController {
  @GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
  public String getText() {
    LOGGER.info("MIME TYPE: {}", MediaType.TEXT_PLAIN_VALUE);
    return "안녕하세요";
  }

  @GetMapping(
      value = "/getSample",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public SampleVO getSample() {
    return new SampleVO(112, "스타", "로드");
  }

  @GetMapping("/getSample2")
  public SampleVO getSample2() {
    return new SampleVO(113, "로켓", "라쿤");
  }
}
