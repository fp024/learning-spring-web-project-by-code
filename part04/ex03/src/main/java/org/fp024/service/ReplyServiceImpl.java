package org.fp024.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyPageDTO;
import org.fp024.domain.ReplyVO;
import org.fp024.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

  private final ReplyMapper replyMapper;

  @Override
  public void register(ReplyVO vo) {
    LOGGER.info("register.....{}", vo);
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

  @Override
  public int remove(Long rno) {
    LOGGER.info("remove.....{}", rno);
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
