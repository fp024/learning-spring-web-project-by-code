package org.fp024.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
class BoardRepositoryTest {

  @Autowired BoardRepository repository;

  /*
  Hibernate:
    select
        b1_0.bno,
        b1_0.content,
        b1_0.regdate,
        b1_0.replycnt,
        b1_0.title,
        b1_0.updatedate,
        b1_0.writer
    from
        tbl_board b1_0
    where
        b1_0.bno=?

    binding parameter [1] as [BIGINT] - [1]
    extracted value ([1] : [BIGINT]) - [1]
    extracted value ([2] : [VARCHAR]) - [게시물 001 본문]
    extracted value ([3] : [TIMESTAMP]) - [2022-12-01T00:00]
    extracted value ([4] : [INTEGER]) - [0]
    extracted value ([5] : [VARCHAR]) - [게시물 001 제목]
    extracted value ([6] : [TIMESTAMP]) - [2022-12-01T00:00]
    extracted value ([7] : [VARCHAR]) - [게시물 001 작성자]
   */
  @DisplayName("게시물 번호로 게시물 조회")
  @Test
  void findById() {
    Long bno = 1L;
    repository.findById(bno);
  }

  /*
  Hibernate:
    delete
    from
        tbl_board
    where
        bno=?

    binding parameter [1] as [BIGINT] - [1]
   */
  @DisplayName("게시물 번호로 게시물 지우기, 삭제된 행 개수 알고 싶어서 Specification 인자 전달방식으로 사용했음.")
  @Transactional
  @Test
  void delete() {
    Long bno = 1L;
    assertThat(repository.delete((root, query, cb) -> cb.equal(root.get("bno"), bno)))
        .isEqualTo(1L);
  }
}
