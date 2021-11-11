package org.fp024.service;

import java.util.List;

import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;
import org.fp024.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
  // Spring 4.3이상에서 자동처리 (단일 파라미터 생성자에 대해서는 자동 주입)
  // 모든 인자에 대한 생성자를 자동으로 만들도록 lombok에서 정의했음.
  private BoardMapper mapper;

  @Override
  public void register(BoardVO board) {
    LOGGER.info("register..... {}", board);
    mapper.insertSelectKey(board);
  }

  @Override
  public BoardVO get(Long bno) {
    LOGGER.info("get..... {}", bno);
    return mapper.read(bno);
  }

  @Override
  public boolean modify(BoardVO board) {
    LOGGER.info("modify..... {}", board);
    return mapper.update(board) == 1;
  }

  @Override
  public boolean remove(Long bno) {
    LOGGER.info("remove..... {}", bno);
    return mapper.delete(bno) == 1;
  }

  @Override
  public List<BoardVO> getList(Criteria criteria) {
    LOGGER.info("get List with criteria: {}", criteria);
    return mapper.getListWithPaging(criteria);
  }
}
