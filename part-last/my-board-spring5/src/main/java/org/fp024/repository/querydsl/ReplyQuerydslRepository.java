package org.fp024.repository.querydsl;

import static org.fp024.domain.QReplyVO.replyVO;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyVO;
import org.springframework.stereotype.Repository;

/** 댓글 리포지토리 - Querydsl 사용 */
@RequiredArgsConstructor
@Repository
public class ReplyQuerydslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public List<ReplyVO> select(Criteria cri, Long boardNo) {
    return jpaQueryFactory
        .selectFrom(replyVO)
        .where(replyVO.bno.eq(boardNo).and(replyVO.rno.gt(0L)))
        .orderBy(replyVO.bno.asc())
        .offset(cri.getOffset())
        .limit(cri.getAmount())
        .fetch();
  }

  public int update(ReplyVO vo) {
    return (int)
        jpaQueryFactory
            .update(replyVO) //
            .set(replyVO.reply, vo.getReply())
            .set(replyVO.updateDate, LocalDateTime.now())
            .where(replyVO.rno.eq(vo.getRno()))
            .execute();
  }

  public int delete(Long rno) {
    return (int)
        jpaQueryFactory
            .delete(replyVO) //
            .where(replyVO.rno.eq(rno))
            .execute();
  }

  public int count(Long boardNo) {
    return Objects.requireNonNull(
            jpaQueryFactory
                .select(replyVO.count())
                .from(replyVO)
                .where(replyVO.bno.eq(boardNo))
                .fetchOne())
        .intValue();
  }

// TODO: 현재 환경에서 Querydsl의 insert()로는 오류가난다.
// https://github.com/fp024/learning-spring-web-project-by-code/issues/21
//  public void save(ReplyVO vo) {
//    jpaQueryFactory
//        .insert(replyVO)
//        .set(replyVO.reply, vo.getReply())
//        .set(replyVO.replyer, vo.getReplyer())
//        .set(replyVO.bno, vo.getBno())
//        .execute();
//  }
}
