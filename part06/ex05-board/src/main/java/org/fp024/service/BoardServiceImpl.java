package org.fp024.service;

import static org.fp024.util.CommonUtil.winPathToUnixPath;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.BoardAttachVO;
import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;
import org.fp024.mapper.BoardAttachMapper;
import org.fp024.mapper.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
  // Spring 4.3이상에서 자동처리 (단일 파라미터 생성자에 대해서는 자동 주입)
  // 모든 인자에 대한 생성자를 자동으로 만들도록 lombok에서 정의했음.
  private final BoardMapper mapper;

  private final BoardAttachMapper attachMapper;

  @Transactional
  @Override
  public void register(BoardVO board) {
    LOGGER.info("register..... {}", board);
    mapper.insertSelectKey(board);

    if (board.getAttachList() == null || board.getAttachList().isEmpty()) {
      return;
    }

    board
        .getAttachList()
        .forEach(
            attach -> {
              attach.setBno(
                  board.getBno()); // 신규 등록시는 최초에 bno가 없지만 insert 이후로 board에다 bno를 MyBatis가 넣어줄 것 임.
              // 업로드 경로를 DB에 저장을 할 때만, Unix 경로로 사용해보자!,
              // 요즘 윈도우에서는 Unix 경로를 쓰더라도 잘 될 것 같은데... 조회 추가해보면서 확인해보자.
              attach.setUploadPath(winPathToUnixPath(attach.getUploadPath()));
              attachMapper.insert(attach);
            });
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

  @Transactional
  @Override
  public boolean remove(Long bno) {
    LOGGER.info("remove..... {}", bno);
    attachMapper.deleteAll(bno);
    return mapper.delete(bno) == 1;
  }

  @Override
  public List<BoardVO> getList(Criteria criteria) {
    LOGGER.info("get List with criteria: {}", criteria);
    return mapper.getListWithPaging(criteria);
  }

  @Override
  public long getTotal(Criteria criteria) {
    LOGGER.info("get total count");
    return mapper.getTotalCount(criteria).get("count");
  }

  @Override
  public List<BoardAttachVO> getAttachList(Long bno) {
    LOGGER.info("get Attach list by bno {}", bno);
    return attachMapper.findByBno(bno);
  }
}
