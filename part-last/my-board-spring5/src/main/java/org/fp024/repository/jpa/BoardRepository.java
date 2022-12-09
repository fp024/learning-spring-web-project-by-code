package org.fp024.repository.jpa;

import org.fp024.domain.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/** 게시물 리포지토리 */
public interface BoardRepository
    extends JpaRepository<BoardVO, Long>, JpaSpecificationExecutor<BoardVO> {
  long deleteByBno(Long bno);
}
