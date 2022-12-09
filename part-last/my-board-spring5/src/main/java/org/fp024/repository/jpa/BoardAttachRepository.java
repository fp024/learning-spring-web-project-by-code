package org.fp024.repository.jpa;

import java.util.List;
import org.fp024.domain.BoardAttachVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/** 게시물 첨부파일 리포지토리 - Spring Data JPA */
public interface BoardAttachRepository
    extends JpaRepository<BoardAttachVO, Long>, JpaSpecificationExecutor<BoardAttachVO> {
  long deleteByBno(Long bno);

  List<BoardAttachVO> findByUploadPath(String folderYesterdayUnixPath);

  List<BoardAttachVO> findByBno(Long bno);
}
