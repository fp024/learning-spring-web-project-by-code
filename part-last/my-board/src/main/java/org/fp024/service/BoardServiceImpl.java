package org.fp024.service;

import static org.fp024.util.CommonUtil.currentSystemPathToUnixPath;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.BoardAttachVO;
import org.fp024.domain.BoardAttachVO_;
import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;
import org.fp024.repository.jpa.BoardAttachRepository;
import org.fp024.repository.jpa.BoardRepository;
import org.fp024.repository.querydsl.BoardQuerydslRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mybatis Dynamic SQL 에서... <br>
 * Spring Data JPA, Querydsl 로 전환 <br>
 *
 * @author fp024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
  private final BoardRepository boardRepository;

  private final BoardQuerydslRepository boardQuerydslRepository;

  private final BoardAttachRepository attachRepository;

  @Transactional
  @Override
  public void register(BoardVO board) {
    LOGGER.info("register..... {}", board);
    board.setRegdate(null);
    board.setUpdateDate(null);
    boardRepository.save(board);

    if (board.getAttachList() == null || board.getAttachList().isEmpty()) {
      return;
    }

    board
        .getAttachList()
        .forEach(
            attach -> {
              attach.setBno(board.getBno());
              attach.setUploadPath(currentSystemPathToUnixPath(attach.getUploadPath()));
              attachRepository.save(attach);
            });
  }

  @Override
  public BoardVO get(Long bno) {
    LOGGER.info("get..... {}", bno);
    return boardRepository.findById(bno).orElse(null);
  }

  @Transactional
  @Override
  public boolean modify(BoardVO board) {
    LOGGER.info("modify..... {}", board);
    attachRepository.delete(
        (root, query, cb) -> cb.equal(root.get(BoardAttachVO_.bno), board.getBno()));

    boolean modifyResult = boardQuerydslRepository.update(board) == 1;

    if (modifyResult && board.getAttachList() != null && !board.getAttachList().isEmpty()) {
      board
          .getAttachList()
          .forEach(
              attach -> {
                attach.setBno(board.getBno());
                attach.setUploadPath(currentSystemPathToUnixPath(attach.getUploadPath()));
                attachRepository.save(attach);
              });
    }

    return modifyResult;
  }

  @Transactional
  @Override
  public boolean remove(Long bno) {
    LOGGER.info("remove..... {}", bno);
    attachRepository.delete((root, query, cb) -> cb.equal(root.get(BoardAttachVO_.bno), bno));
    return boardRepository.delete((root, query, cb) -> cb.equal(root.get("bno"), bno)) == 1L;
  }

  @Override
  public List<BoardVO> getList(Criteria criteria) {
    LOGGER.info("get List with criteria: {}", criteria);
    return boardQuerydslRepository.list(criteria);
  }

  @Override
  public long getTotal(Criteria criteria) {
    return boardQuerydslRepository.count(criteria);
  }

  @Override
  public void updateReplyCount(Long bno, int amount) {
    boardQuerydslRepository.updateReplyCount(bno, amount);
  }

  @Override
  public List<BoardAttachVO> getAttachList(Long bno) {
    LOGGER.info("get Attach list by bno {}", bno);
    return attachRepository.findAll(
        (root, query, cb) -> cb.equal(root.get(BoardAttachVO_.bno), bno));
  }
}
