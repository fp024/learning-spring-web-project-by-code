package org.fp024.repository.querydsl;

import org.fp024.config.QuerydslConfig;
import org.fp024.config.RootConfig;
import org.fp024.domain.Criteria;
import org.fp024.repository.querydsl.ReplyQuerydslRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringJUnitConfig(classes = {RootConfig.class, QuerydslConfig.class})
class ReplyQuerydslRepositoryTest {

  @Autowired
  private ReplyQuerydslRepository repository;

  @Test
  void select() {
    Criteria cri = new Criteria();
    Long bno = 1L;
    repository.select(cri , bno);
  }

  @Test
  void updateReplyCount() {
  }

  @Test
  void update() {
  }

  @Test
  void delete() {
  }

  @Test
  void count() {
  }
}