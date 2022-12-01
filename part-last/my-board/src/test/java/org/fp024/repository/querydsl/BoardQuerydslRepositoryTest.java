package org.fp024.repository.querydsl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
class BoardQuerydslRepositoryTest {
  @Autowired private BoardQuerydslRepository repository;

  /*
  Hibernate:
    update
        tbl_board
    set
        replycnt=(replycnt+cast(? as integer))
    where
        bno=?
    binding parameter [1] as [INTEGER] - [-1]
    binding parameter [2] as [BIGINT] - [1]
   */
  @DisplayName("게시물의 댓글 개수 수정")
  @Transactional
  @Test
  void updateReplyCount() {
    Long bno = 1L;
    int amount = -1;
    repository.updateReplyCount(bno, amount);
  }

  /*
  Hibernate:
    update
        tbl_board
    set
        title=?,
        content=?,``
        updatedate=?
    where
        bno=?

    binding parameter [1] as [VARCHAR] - [게시물 제목]
    binding parameter [2] as [VARCHAR] - [게시물 내용]
    binding parameter [3] as [TIMESTAMP] - [2022-12-01T13:39:38.396696800]
    binding parameter [4] as [BIGINT] - [1]
   */
  @DisplayName("게시물 수정")
  @Transactional
  @Test
  void update() {
    BoardVO board = new BoardVO();
    board.setTitle("게시물 제목");
    board.setContent("게시물 내용");
    board.setUpdateDate(LocalDateTime.now());
    board.setBno(1L);

    repository.update(board);
  }

  /*
   게시물 카운트 쿼리

   Hibernate:
     select
         count(b1_0.bno)
     from
         tbl_board b1_0
     where
         (
             b1_0.title like ? escape '!'
             or b1_0.content like ? escape '!'
             or b1_0.writer like ? escape '!'
         )
         and b1_0.bno>?

     binding parameter [1] as [VARCHAR] - [%키워드%]
     binding parameter [2] as [VARCHAR] - [%키워드%]
     binding parameter [3] as [VARCHAR] - [%키워드%]
     binding parameter [4] as [BIGINT] - [0]
  */
  @DisplayName("게시물 카운트 쿼리")
  @Test
  void count() {
    Criteria c = new Criteria();
    c.setSearchCodes(List.of("T", "C", "W"));
    c.setKeyword("키워드");

    repository.count(c);
  }

  /*
  Hibernate:
      select
          b1_0.bno,
          b1_0.content,
          b1_0.regdate,
          b1_0.replycnt,
          b1_0.title,
          b1_0.updatedate,
          b1_0.writer
      from
          tbl_board b1_0
      where
          (
              b1_0.title like ? escape '!'
              or b1_0.content like ? escape '!'
              or b1_0.writer like ? escape '!'
          )
          and b1_0.bno>?
      order by
          b1_0.bno desc offset ? rows fetch first ? rows only

      binding parameter [1] as [VARCHAR] - [%키워드%]
      binding parameter [2] as [VARCHAR] - [%키워드%]
      binding parameter [3] as [VARCHAR] - [%키워드%]
      binding parameter [4] as [BIGINT] - [0]
      binding parameter [5] as [INTEGER] - [0]
      binding parameter [6] as [INTEGER] - [10]
   */
  @DisplayName("게시물 페이징 목록 조회")
  @Test
  void list() {
    Criteria c = new Criteria();
    c.setPageNum(1);
    c.setAmount(10);
    c.setSearchCodes(List.of("T", "C", "W"));
    c.setKeyword("키워드");
    repository.list(c);
  }
}
