package org.fp024.repository.querydsl;

import static org.fp024.domain.QBoardVO.boardVO;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;
import org.fp024.domain.SearchType;
import org.springframework.stereotype.Repository;

/** 게시물 리포지토리 - Querydsl 사용 */
@RequiredArgsConstructor
@Repository
public class BoardQuerydslRepository {
  private final JPAQueryFactory jpaQueryFactory;

  public void updateReplyCount(Long bno, int amount) {
    jpaQueryFactory
        .update(boardVO) //
        .set(boardVO.replyCount, boardVO.replyCount.add(amount))
        .where(boardVO.bno.eq(bno))
        .execute();
  }

  public int update(BoardVO boardEntity) {
    return (int)
        jpaQueryFactory
            .update(boardVO) //
            .set(boardVO.title, boardEntity.getTitle())
            .set(boardVO.content, boardEntity.getContent())
            .set(boardVO.updateDate, LocalDateTime.now())
            .where(boardVO.bno.eq(boardEntity.getBno()))
            .execute();
  }

  public long count(Criteria criteria) {
    return Objects.requireNonNull(
        jpaQueryFactory
            .select(boardVO.count())
            .from(boardVO)
            .where(createSearchCondition(criteria).and(boardVO.bno.gt(0)))
            .fetchOne());
  }

  public List<BoardVO> list(Criteria criteria) {
    return jpaQueryFactory //
        .selectFrom(boardVO)
        .where(createSearchCondition(criteria).and(boardVO.bno.gt(0)))
        .orderBy(boardVO.bno.desc())
        .offset(criteria.getOffset())
        .limit(criteria.getAmount())
        .fetch();
  }

  /** 검색 조건 생성 */
  private BooleanBuilder createSearchCondition(Criteria criteria) {
    BooleanBuilder builder = new BooleanBuilder();
    for (SearchType searchType : criteria.getSearchTypeSet()) {
      builder.or(searchType.getPath().contains(criteria.getKeyword()));
    }
    return builder;
  }
}
