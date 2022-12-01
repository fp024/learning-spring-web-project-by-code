package org.fp024.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyPageDTO;
import org.fp024.domain.ReplyVO;
import org.fp024.repository.jpa.ReplyRepository;
import org.fp024.repository.querydsl.BoardQuerydslRepository;
import org.fp024.repository.querydsl.ReplyQuerydslRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
  private final ReplyRepository replyRepository;

  private final ReplyQuerydslRepository replyQuerydslRepository;

  private final BoardQuerydslRepository boardQuerydslRepository;

  @Transactional
  @Override
  public void register(ReplyVO vo) {
    LOGGER.info("register.....{}", vo);
    boardQuerydslRepository.updateReplyCount(vo.getBno(), 1);
    replyQuerydslRepository.save(vo);
  }

  @Override
  public ReplyVO get(Long rno) {
    LOGGER.info("get.....{}", rno);
    return replyRepository.findById(rno).orElse(null);
  }

  @Transactional
  @Override
  public int modify(ReplyVO vo) {
    LOGGER.info("modify.....{}", vo);
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

    boardQuerydslRepository.updateReplyCount(optional.get().getBno(), -1);
    return replyQuerydslRepository.delete(rno);
  }

  @Override
  public ReplyPageDTO getListPage(Criteria cri, Long boardNo) {
    LOGGER.info("get Reply List of a board {}", boardNo);
    return new ReplyPageDTO(
        cri.getAmount(), getCount(boardNo), replyQuerydslRepository.select(cri, boardNo));
  }

  private int getCount(Long boardNo) {
    return replyQuerydslRepository.count(boardNo);
  }
}
