package org.fp024.repository.querydsl;

import static org.fp024.domain.QBoardVO.boardVO;
import static org.fp024.domain.QReplyVO.replyVO;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyVO;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReplyQuerydslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public List<ReplyVO> select(Criteria cri, Long boardNo) {
    return jpaQueryFactory
        .selectFrom(replyVO)
        .where(replyVO.bno.eq(boardNo).and(replyVO.rno.gt(0L)))
        .orderBy(replyVO.bno.desc())
        .offset((cri.getPageNum() - 1) * cri.getAmount())
        .limit(cri.getAmount())
        .fetch();
  }

  // TODO: BoardVO를 수정하는 거라서 옮길지? 검토필요
  public void updateReplyCount(Long bno, int amount) {
    jpaQueryFactory
        .update(boardVO) //
        .set(boardVO.replyCount, boardVO.replyCount.add(amount))
        .where(boardVO.bno.eq(bno))
        .execute();
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
    return jpaQueryFactory
        .select(replyVO.count())
        .from(replyVO)
        .where(replyVO.bno.eq(boardNo))
        .fetchOne()
        .intValue();
  }
}
