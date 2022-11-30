package org.fp024.repository.jpa;

import org.fp024.domain.ReplyVO;
import org.springframework.data.jpa.repository.JpaRepository;

/** 댓글 리포지토리 */
public interface ReplyRepository extends JpaRepository<ReplyVO, Long> {}
