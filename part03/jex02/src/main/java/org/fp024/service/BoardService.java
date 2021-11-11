package org.fp024.service;

import java.util.List;

import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;

public interface BoardService {
  void register(BoardVO board);

  BoardVO get(Long bno);

  boolean modify(BoardVO board);

  boolean remove(Long bno);

  List<BoardVO> getList(Criteria criteria);
}
