package org.fp024.repository.jpa;

import org.fp024.config.RootConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {RootConfig.class})
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
}
