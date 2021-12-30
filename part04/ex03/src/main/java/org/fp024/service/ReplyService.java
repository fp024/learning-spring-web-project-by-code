package org.fp024.service;

import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyVO;

import java.util.List;

public interface ReplyService {
  void register(ReplyVO vo);

  ReplyVO get(Long rno);

  int modify(ReplyVO vo);

  int remove(Long rno);

  List<ReplyVO> getList(Criteria cri, Long bno);
}
