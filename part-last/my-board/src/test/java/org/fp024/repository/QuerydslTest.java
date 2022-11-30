package org.fp024.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.domain.QMemberVO;
import org.fp024.domain.QReplyVO;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** Querydsl 테스트 */
@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
public class QuerydslTest {

  @PersistenceContext
  private EntityManager em;

  @Test
  void test() {
    JPAQueryFactory query = new JPAQueryFactory(em);
    QMemberVO qMemberVO = new QMemberVO("m");

    query.selectFrom(qMemberVO).fetchFirst();





  }


}
