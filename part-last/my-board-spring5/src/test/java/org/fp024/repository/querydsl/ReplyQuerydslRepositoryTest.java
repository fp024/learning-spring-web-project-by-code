package org.fp024.repository.querydsl;

import java.time.LocalDateTime;
import org.fp024.config.RootConfig;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(classes = {RootConfig.class})
class ReplyQuerydslRepositoryTest {

  @Autowired private ReplyQuerydslRepository repository;

  /*
  Hibernate:
    select
        r1_0.rno,
        r1_0.bno,
        r1_0.reply,
        r1_0.replydate,
        r1_0.replyer,
        r1_0.updatedate
    from
        tbl_reply r1_0
    where
        r1_0.bno=?
        and r1_0.rno>?
    order by
        r1_0.bno desc offset ? rows fetch first ? rows only
    binding parameter [1] as [BIGINT] - [1]
    binding parameter [2] as [BIGINT] - [0]
    binding parameter [3] as [INTEGER] - [20]
    binding parameter [4] as [INTEGER] - [20]
   */
  @DisplayName("댓글 목록 조회")
  @Test
  void select() {
    Criteria c = new Criteria();
    c.setPageNum(2);
    c.setAmount(20); // 페이지 크기가 10, 20, 50으로 제한되어있음
    Long bno = 1L;
    repository.select(c, bno);
  }

  /*
  Hibernate:
    update
        tbl_reply
    set
        reply=?,
        updatedate=?
    where
        rno=?

  binding parameter [1] as [VARCHAR] - [댓글 내용]
  binding parameter [2] as [TIMESTAMP] - [2022-12-01T14:07:45.199167400]
  binding parameter [3] as [BIGINT] - [1]
   */
  @DisplayName("댓글 수정")
  @Transactional
  @Test
  void update() {
    ReplyVO r = new ReplyVO();
    r.setReply("댓글 내용");
    r.setUpdateDate(LocalDateTime.now());
    r.setRno(1L);

    repository.update(r);
  }

  /*
  Hibernate:
   delete
   from
       tbl_reply
   where
       rno=?
   binding parameter [1] as [BIGINT] - [1]
  */
  @DisplayName("댓글 번호로 댓글 삭제")
  @Transactional
  @Test
  void delete() {
    Long rno = 1L;
    repository.delete(rno);
  }

  /*
   Hibernate:
     select
         count(r1_0.rno)
     from
         tbl_reply r1_0
     where
         r1_0.bno=?
     binding parameter [1] as [BIGINT] - [1]
  */
  @DisplayName("게시물에 속한 댓글 카운트")
  @Test
  void count() {
    Long bno = 1L;
    repository.count(bno);
  }


}
