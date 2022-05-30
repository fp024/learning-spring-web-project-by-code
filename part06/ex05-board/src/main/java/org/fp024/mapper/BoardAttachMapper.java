package org.fp024.mapper;

import org.fp024.domain.BoardAttachVO;

import java.util.List;

/** 첨부파일 매퍼 */
public interface BoardAttachMapper {
  /**
   * 첨부 파일 등록
   *
   * @param vo 첨부파일 VO
   */
  void insert(BoardAttachVO vo);

  /**
   * 첨부파일 삭제
   *
   * @param uuid UUID
   */
  void delete(String uuid);

  /**
   * 첨부파일 목록 조회
   *
   * @param bno 게시물 번호
   */
  List<BoardAttachVO> findByBno(Long bno);
}
