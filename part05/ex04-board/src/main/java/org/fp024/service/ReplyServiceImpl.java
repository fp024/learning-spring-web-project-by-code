package org.fp024.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyPageDTO;
import org.fp024.domain.ReplyVO;
import org.fp024.mapper.BoardMapper;
import org.fp024.mapper.ReplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

  private final ReplyMapper replyMapper;

  private final BoardMapper boardMapper;

  @Transactional
  @Override
  public void register(ReplyVO vo) {
    LOGGER.info("register.....{}", vo);

    boardMapper.updateReplyCount(vo.getBno(), 1);

    replyMapper.insert(vo);
  }

  @Override
  public ReplyVO get(Long rno) {
    LOGGER.info("get.....{}", rno);
    return replyMapper.read(rno);
  }

  @Override
  public int modify(ReplyVO vo) {
    LOGGER.info("modify.....{}", vo);
    return replyMapper.update(vo);
  }

  @Transactional
  @Override
  public int remove(Long rno) {
    LOGGER.info("remove.....{}", rno);

    ReplyVO vo = replyMapper.read(rno);

    if (vo == null) {
      return 0;
    }

    boardMapper.updateReplyCount(vo.getBno(), -1);

    return replyMapper.delete(rno);
  }

  @Override
  public List<ReplyVO> getList(Criteria cri, Long bno) {
    LOGGER.info("get Reply List of a board {}", bno);
    return replyMapper.getListWithPaging(cri, bno);
  }

  @Override
  public ReplyPageDTO getListPage(Criteria cri, Long bno) {
    return new ReplyPageDTO(
        cri.getAmount(), replyMapper.getCountByBno(bno), replyMapper.getListWithPaging(cri, bno));
  }
}
