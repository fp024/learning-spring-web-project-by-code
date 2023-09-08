package org.fp024.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.fp024.config.RootConfig;
import org.fp024.domain.BoardAttachVO;
import org.fp024.domain.BoardAttachVO_;
import org.fp024.domain.FileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(classes = {RootConfig.class})
class BoardAttachRepositoryTest {
  @Autowired private BoardAttachRepository repository;

  /*
  저장 전에 생성된 기본키로 먼저 알아서 조회를 하는 모습을 보임.

   Hibernate:
    select
        b1_0.uuid,
        b1_0.bno,
        b1_0.filename,
        b1_0.filetype,
        b1_0.uploadpath
    from
        tbl_attach b1_0
    where
        b1_0.uuid=?
    binding parameter [1] as [VARCHAR] - [e2ac4f21-6906-4bd3-8317-864cf73face6]

  Hibernate:
    insert
    into
        tbl_attach
        (bno, filename, filetype, uploadpath, uuid)
    values
        (?, ?, ?, ?, ?)
    binding parameter [1] as [BIGINT] - [1]
    binding parameter [2] as [VARCHAR] - [이미지_파일.png]
    binding parameter [3] as [VARCHAR] - [I]
    binding parameter [4] as [VARCHAR] - [2022/12/01]
    binding parameter [5] as [VARCHAR] - [e2ac4f21-6906-4bd3-8317-864cf73face6]
   */
  // @Transactional // 이것을 설정하면 DB에 반영하진 않음.,
  // 그런데 INSERT는 필수로 넣는것이 아닌가? delete나 update할 때는 넣지 않으면 예외 발생하는데..
  @DisplayName("게시물에 첨부파일 등록")
  @Test
  void save() {
    BoardAttachVO ba = new BoardAttachVO();
    ba.setBno(1L);
    ba.setUuid(UUID.randomUUID().toString());
    ba.setUploadPath("2022/12/01");
    ba.setFileType(FileType.IMAGE);
    ba.setFileName("이미지_파일.png");

    repository.save(ba);
  }

  /*
  Hibernate:
    delete
    from
        tbl_attach
    where
        bno=?
    binding parameter [1] as [BIGINT] - [1]
   */
  @DisplayName("게시물에 속한 첨부파일 전부 삭제")
  @Transactional
  @Test
  void delete() {
    Long bno = 1L;
    long deleteRowCount =
        repository.delete((root, query, cb) -> cb.equal(root.get(BoardAttachVO_.bno), bno));
    assertThat(deleteRowCount).isEqualTo(1);
  }

  /*
  Hibernate:
    select
        b1_0.uuid,
        b1_0.bno,
        b1_0.filename,
        b1_0.filetype,
        b1_0.uploadpath
    from
        tbl_attach b1_0
    where
        b1_0.bno=?

    binding parameter [1] as [BIGINT] - [1]
    extracted value ([1] : [VARCHAR]) - [23594eb5-e6fc-4b9c-991f-e218cab6b85d]
    extracted value ([2] : [BIGINT]) - [1]
    extracted value ([3] : [VARCHAR]) - [이미지_파일.png]
    extracted value ([4] : [VARCHAR]) - [I]
    extracted value ([5] : [VARCHAR]) - [2022/12/01]
   */
  @DisplayName("특정 게시물에 속한 첨부파일 조회")
  @Test
  void findAll_01() {
    Long bno = 1L;
    repository.findAll((root, query, cb) -> cb.equal(root.get(BoardAttachVO_.bno), bno));
  }

  /*
  Hibernate:
   select
       b1_0.uuid,
       b1_0.bno,
       b1_0.filename,
       b1_0.filetype,
       b1_0.uploadpath
   from
       tbl_attach b1_0
   where
       b1_0.uploadpath=?

   binding parameter [1] as [VARCHAR] - [2022/12/01]
  */
  @DisplayName("특정 업로드 경로(년/월/일)의 첨부파일 정보 조회)")
  @Test
  void findAll_02() {
    repository.findAll(
        (root, query, cb) -> cb.equal(root.get(BoardAttachVO_.uploadPath), "2022/12/01"));
  }

  @DisplayName("특정 업로드 경로(년/월/일)의 첨부파일 정보 조회) - d이름 방식")
  @Test
  void findAll_03() {
    repository.findByUploadPath("2022/12/01");
  }
}
