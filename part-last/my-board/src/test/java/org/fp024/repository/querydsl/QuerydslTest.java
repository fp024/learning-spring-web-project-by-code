package org.fp024.repository.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.fp024.config.RootConfig;
import org.fp024.domain.QMemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** Querydsl 테스트 */
@SpringJUnitConfig(classes = {RootConfig.class})
public class QuerydslTest {
  @Autowired private JPAQueryFactory jpaQueryFactory;

  @Test
  void test() {

    QMemberVO qMemberVO = new QMemberVO("m");

    String result =
        jpaQueryFactory
            .select(qMemberVO.userId)
            .from(qMemberVO) //
            .where(qMemberVO.userId.eq("admin90"))
            .fetchOne();

    assertThat(result) //
        .isNotNull()
        .isEqualTo("admin90");
  }
}
