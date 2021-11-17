package org.fp024.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.fp024.config.RootConfig;
import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;
import org.fp024.domain.PageSize;
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

  // 쿼리에 INDEX 힌트가 있어서 한줄 주석을 사용했다.
  //   select BNO, TITLE, CONTENT, WRITER, REGDATE, UPDATEDATE
  //     from
  //           (select /*+ INDEX_DESC(tbl_board pk_board) */ 'dummy'
  //                , ROWNUM as rn, BNO, TITLE, CONTENT, WRITER, REGDATE, UPDATEDATE from TBL_BOARD
  //            where (TITLE like ? or CONTENT like ? or WRITER like ?)
  //              and ROWNUM <= ?
  //           )
  //    where rn > ? order by BNO DESC
  @Test
  void testGetList() {
    Criteria criteria = new Criteria(1, PageSize.SIZE_10);
    criteria.setSearchCodes(Arrays.asList("T", "C", "W"));
    criteria.setKeyword("newbie");
    service.getList(criteria).forEach(board -> LOGGER.info(board.toString()));
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

  /*
    select count(*)
      from TBL_BOARD
     where (TITLE like ? or CONTENT like ? or WRITER like ?)
       and BNO > ?
  */
  @Test
  void testGetCount() {
    Criteria criteria = new Criteria();
    criteria.setSearchCodes(Arrays.asList("T", "W"));
    criteria.setKeyword("검색어");
    service.getTotal(criteria);
  }
}
