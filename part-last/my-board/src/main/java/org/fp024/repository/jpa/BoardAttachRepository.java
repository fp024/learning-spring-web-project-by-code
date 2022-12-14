package org.fp024.repository.jpa;

import java.util.List;
import org.fp024.domain.BoardAttachVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/** 게시물 첨부파일 리포지토리 - Spring Data JPA */
public interface BoardAttachRepository
    extends JpaRepository<BoardAttachVO, Long>, JpaSpecificationExecutor<BoardAttachVO> {
  // FileCheckTask 테스트 코드 만들때.
  // Specification로 처리한 메서드가 Mock의 when으로 넣기가 좀 애매해서
  // 이름 기반 메서드 만듬.
  List<BoardAttachVO> findByUploadPath(String uploadPath);
}
