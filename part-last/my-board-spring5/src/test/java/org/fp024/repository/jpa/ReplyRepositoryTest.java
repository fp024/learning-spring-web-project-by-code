package org.fp024.repository.jpa;

import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.domain.ReplyVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
class ReplyRepositoryTest {
  @Autowired private ReplyRepository repository;

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
        r1_0.rno=?

    binding parameter [1] as [BIGINT] - [1]
   */
  @DisplayName("댓글 번호로 댓글 조회")
  @Test
  void findById() {
    Long rno = 1L;
    repository.findById(rno);
  }

  /* // 등록일시에 대해 DB의 기본 날짜로 자동처리되게하려고, 필수 컬럼만 INSERT 되게 했음.
  Hibernate:
   insert
   into
       tbl_reply
       (reply,replyer,bno)
   values
       (?,?,?)

   binding parameter [1] as [VARCHAR] - [신규 댓글 내용]
   binding parameter [2] as [VARCHAR] - [댓글 작성자]
   binding parameter [3] as [BIGINT] - [1]
  */
  @DisplayName("댓글 저장")
  @Transactional
  @Test
  void save() {
    ReplyVO r = new ReplyVO();
    r.setReply("신규 댓글 내용");
    r.setReplyer("댓글 작성자");
    r.setBno(1L); // 연관 설정은 안했지만, 따로 외래키 설정을 해놔서, 1번 게시물이 존재해야 댓글이 등록된다.
    repository.save(r);
  }
}
