package org.fp024.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.fp024.config.RootConfig;
import org.fp024.domain.BoardVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@Slf4j
class BoardServiceTest {
  @Autowired private BoardService service;

  @Test
  void testExist() {
    assertNotNull(service);
    LOGGER.info(service.toString());
  }

  @Test
  void testRegister() {
    BoardVO board = new BoardVO();
    board.setTitle("1새로 작성하는 글");
    board.setContent("새로 작성하는 내용");
    board.setWriter("newbie");

    service.register(board);

    LOGGER.info("생성된 게시물 번호: {}", board.getBno());
  }

  @Test
  void testGetList() {
    service.getList().forEach(board -> LOGGER.info(board.toString()));
  }

  @Test
  void testGet() {
    LOGGER.info("{}", service.get(1L));
  }

  @Test
  void testDelete() {
    // 게시물 존재 여부에 따라 결과가 달라짐
    LOGGER.info("REMOVE RESULT: {}", service.remove(122L));
  }

  @Test
  void testUpdate() {
    BoardVO board = service.get(1L);

    if (board == null) {
      return;
    }

    board.setTitle("제목 수정합니다. - " + LocalDateTime.now().getSecond());
    LOGGER.info("MODIFY RESULT: {}", service.modify(board));
  }
}
