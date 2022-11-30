package org.fp024.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.domain.QMemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** Querydsl 테스트 */
@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
public class QuerydslTest {

  @PersistenceContext private EntityManager em;

  @Test
  void test() {
    JPAQueryFactory query = new JPAQueryFactory(em);
    QMemberVO qMemberVO = new QMemberVO("m");

    String result =
        query
            .select(qMemberVO.userId)
            .from(qMemberVO) //
            .where(qMemberVO.userId.eq("admin90"))
            .fetchOne();

    assertThat(result) //
        .isNotNull()
        .isEqualTo("admin90");
  }
}
