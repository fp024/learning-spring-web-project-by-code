package org.fp024.mapper;

import java.util.List;

import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;

public interface BoardMapper {  
  List<BoardVO> getListWithPaging(Criteria criteria);

  void insert(BoardVO board);

  void insertSelectKey(BoardVO board);

  BoardVO read(Long bno);

  int delete(Long bno);

  int update(BoardVO board);
}
