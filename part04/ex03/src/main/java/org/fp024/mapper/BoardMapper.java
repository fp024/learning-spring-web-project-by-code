package org.fp024.mapper;

import java.util.List;
import java.util.Map;

import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;

public interface BoardMapper {
  List<BoardVO> getListWithPaging(Criteria criteria);

  void insert(BoardVO board);

  void insertSelectKey(BoardVO board);

  BoardVO read(Long bno);

  int delete(Long bno);

  int update(BoardVO board);

  Map<String, Long> getTotalCount(Criteria criteria);
}
