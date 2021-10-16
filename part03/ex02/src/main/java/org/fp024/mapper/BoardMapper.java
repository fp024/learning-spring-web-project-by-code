package org.fp024.mapper;

import java.util.List;

import org.fp024.domain.BoardVO;

public interface BoardMapper {
  List<BoardVO> getList();

  void insert(BoardVO board);

  void insertSelectKey(BoardVO board);

  BoardVO read(Long bno);

  int delete(Long bno);

  int update(BoardVO board);
}
