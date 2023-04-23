package org.fp024.repository.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.domain.QBoardVO;
import org.fp024.domain.QMemberVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

/** Querydsl 테스트 */
@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
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

  /*
    Hibernate 5.6까지는 INSERT-SELECT 만 지원한다.

    5.6 (INSERT-SELECT 만 지원)
     - https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#_hql_syntax_for_insert

    6.0 (INSERT-SELECT, INSERT-VALUES 둘 다 지원)
     - https://docs.jboss.org/hibernate/orm/6.0/userguide/html_single/Hibernate_User_Guide.html#_hql_syntax_for_insert
  */
  @Disabled("Hibernate 5.6에서는 INSERT-SELECT만 지원하기 때문에, 아래 쿼리는 HQL Syntax 오류가 발생")
  @Transactional
  @Test
  void saveBoard() {
    QBoardVO qBoardVO = new QBoardVO("b");

    long result =
        jpaQueryFactory
            .insert(qBoardVO)
            .columns(
                qBoardVO.title,
                qBoardVO.content,
                qBoardVO.writer,
                qBoardVO.regdate,
                qBoardVO.updateDate,
                qBoardVO.replyCount)
            .values(
                "게시물 003 제목", //
                "게시물 003 본문",
                "admin90",
                LocalDateTime.now(),
                LocalDateTime.now(),
                0)
            .execute();

    assertThat(result).isEqualTo(1);
  }
}
