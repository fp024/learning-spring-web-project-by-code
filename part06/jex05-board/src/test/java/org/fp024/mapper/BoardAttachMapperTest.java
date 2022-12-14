package org.fp024.mapper;

import static org.fp024.mapper.BoardAttachVODynamicSqlSupport.bno;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import java.util.List;
import java.util.UUID;
import org.fp024.config.RootConfig;
import org.fp024.domain.BoardAttachVO;
import org.fp024.domain.FileType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * 첨부파일 매퍼 테스트
 *
 * <p>이 프로젝트에서는 mapper를 직접 사용하기보단 서비스 클래스를 만들어서 사용하는 것이 나을 수도 있다.
 */
@SpringJUnitConfig(classes = {RootConfig.class})
class BoardAttachMapperTest {

  @Autowired private BoardAttachMapper mapper;

  private static final String TEST_UUID = UUID.randomUUID().toString();
  private static final String UPLOAD_PATH = "2022/05/30";
  private static final String FILE_NAME = "이미지_파일.png";
  private static final FileType FILE_TYPE = FileType.IMAGE;
  private static final Long BNO = 1L;

  @Transactional
  @Test
  void testInsert() {
    BoardAttachVO vo = new BoardAttachVO();

    vo.setUuid(TEST_UUID);
    vo.setUploadPath(UPLOAD_PATH);
    vo.setFileName(FILE_NAME);
    vo.setFileType(FILE_TYPE);
    vo.setBno(1L);

    mapper.insert(vo);
  }

  @Transactional
  @Test
  void testDelete() {
    testInsert();
    mapper.deleteByPrimaryKey(TEST_UUID);
  }

  @Transactional
  @Test
  void testFindBno() {
    testInsert();

    List<BoardAttachVO> list =
        mapper.select(
            selectModelQueryExpressionDSL ->
                selectModelQueryExpressionDSL.where(bno, isEqualTo(1L)));

    assertEquals(TEST_UUID, list.get(0).getUuid());
    assertEquals(UPLOAD_PATH, list.get(0).getUploadPath());
    assertEquals(FILE_NAME, list.get(0).getFileName());
    assertEquals(FILE_TYPE, list.get(0).getFileType());
    assertEquals(BNO, list.get(0).getBno());
  }
}
