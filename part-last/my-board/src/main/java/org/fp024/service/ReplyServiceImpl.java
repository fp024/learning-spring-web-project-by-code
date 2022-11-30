package org.fp024.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyPageDTO;
import org.fp024.domain.ReplyVO;
import org.fp024.repository.jpa.ReplyRepository;
import org.fp024.repository.querydsl.ReplyQuerydslRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
  private final ReplyRepository replyRepository;

  private final ReplyQuerydslRepository replyQuerydslRepository;

  @Transactional
  @Override
  public void register(ReplyVO vo) {
    LOGGER.info("register.....{}", vo);
    // 테이블의 댓글 등록일시, 수정일시 기본 값이 SYSDATE 이다.
    vo.setReplyDate(null);
    vo.setUpdateDate(null);

    replyQuerydslRepository.updateReplyCount(vo.getBno(), 1);

    replyRepository.save(vo);
  }

  @Override
  public ReplyVO get(Long rno) {
    LOGGER.info("get.....{}", rno);
    // return replyMapper.selectByPrimaryKey(rno).orElse(null);
    return replyRepository.findById(rno).orElse(null);
  }

  @Override
  public int modify(ReplyVO vo) {
    LOGGER.info("modify.....{}", vo);
    /*
    return replyMapper.update(
        c ->
            c.set(reply)
                .equalTo(vo.getReply())
                .set(updateDate)
                .equalTo(LocalDateTime.now())
                .where(rno, isEqualTo(vo.getRno())));
     */
    return replyQuerydslRepository.update(vo);
  }

  @Transactional
  @Override
  public int remove(Long rno) {
    LOGGER.info("remove.....{}", rno);

    Optional<ReplyVO> optional = replyRepository.findById(rno);

    if (optional.isEmpty()) {
      return 0;
    }

    // boardService.updateReplyCount(optional.get().getBno(), -1);
    replyQuerydslRepository.updateReplyCount(optional.get().getBno(), -1);

    // return replyMapper.deleteByPrimaryKey(rno);

    return replyQuerydslRepository.delete(rno);
  }

  @Override
  public ReplyPageDTO getListPage(Criteria cri, Long boardNo) {
    LOGGER.info("get Reply List of a board {}", boardNo);

    // DerivedColumn<Long> rownum = DerivedColumn.of("ROWNUM");
    // Constant<String> hint = Constant.of("/*+INDEX(tbl_reply idx_reply) */ 'dummy'");
    /*
    List<ReplyVO> list =
        replyMapper.selectMany(
            select(ReplyMapper.selectList)
                .from(
                    select(hint, rownum.as("rn"), rno, bno, reply, replyer, replyDate, updateDate)
                        .from(replyVO)
                        .where(bno, isEqualTo(boardNo))
                        .and(rno, isGreaterThan(0L))
                        .and(rownum, isLessThanOrEqualTo(cri.getPageNum() * cri.getAmount())))
                .where(
                    DerivedColumn.of("rn"), isGreaterThan((cri.getPageNum() - 1) * cri.getAmount()))
                .orderBy(bno.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3));

    */

    return new ReplyPageDTO(
        cri.getAmount(), getCount(boardNo), replyQuerydslRepository.select(cri, boardNo));
  }

  private int getCount(Long boardNo) {
    //    return (int)
    //        replyMapper.count(
    //            select(count())
    //                .from(replyVO)
    //                .where(bno, isEqualTo(boardNo))
    //                .build()
    //                .render(RenderingStrategies.MYBATIS3));

    return replyQuerydslRepository.count(boardNo);
  }
}
