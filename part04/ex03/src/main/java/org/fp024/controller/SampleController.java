package org.fp024.controller;

import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.SampleVO;
import org.fp024.domain.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

  @GetMapping("/getList")
  public List<SampleVO> getList() {
    return IntStream.rangeClosed(1, 10)
        .mapToObj(i -> new SampleVO(i, i + "First", i + " Last"))
        .collect(Collectors.toList());
  }

  @GetMapping("/getMap")
  public Map<String, SampleVO> getMap() {
    Map<String, SampleVO> map = new HashMap<>();
    map.put("First", new SampleVO(111, "그루트", "주니어"));
    return map;
  }

  /*
   @GetMapping의 params와 관련된 내용
      height, height 파라미터 전달이 모두 포함되지 않으면 400 상태코드로 에러 반환
  */
  @GetMapping(
      value = "/check",
      params = {"height", "height"})
  public ResponseEntity<SampleVO> check(Double height, Double weight) {

    // String.valueOf() 입력으로 null을 주면 결과는 "null" 문자열을 반환함. NPE가 발생되지 않음
    SampleVO vo = new SampleVO(0, String.valueOf(height), String.valueOf(weight));
    ResponseEntity<SampleVO> result = null;

    if (height < 150) {
      result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
    } else {
      result = ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    return result;
  }

  @GetMapping("/product/{cat}/{pid}")
  public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
    return new String[] {"category: " + cat, "productId: " + pid};
  }

  @PostMapping("/ticket")
  public Ticket convert(@RequestBody Ticket ticket) {
    LOGGER.info("convert.........ticket: {}", ticket);
    return ticket;
  }
}
