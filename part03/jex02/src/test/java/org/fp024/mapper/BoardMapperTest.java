package org.fp024.mapper;

import static org.fp024.mapper.BoardVODynamicSqlSupport.bno;
import static org.fp024.mapper.BoardVODynamicSqlSupport.content;
import static org.fp024.mapper.BoardVODynamicSqlSupport.regdate;
import static org.fp024.mapper.BoardVODynamicSqlSupport.title;
import static org.fp024.mapper.BoardVODynamicSqlSupport.updateDate;
import static org.fp024.mapper.BoardVODynamicSqlSupport.writer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.fp024.config.RootConfig;
import org.fp024.domain.BoardVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.Constant;
import org.mybatis.dynamic.sql.DerivedColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.StringConstant;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import lombok.extern.slf4j.Slf4j;
/**
 * mybatis-dynamic-sql 으로 만들어진 mapper를 쓸 때, QueryDSL 같이 사용한 부분은 별도 클래스로 분리해서 그것이 mapper클래스를 사용하게 해야할
 * 것 같다.
 *
 * <p>참조:
 *
 * <p>WHERE 절 지원 https://mybatis.org/mybatis-dynamic-sql/docs/whereClauses.html
 *
 * <p>SELECT 문장 https://mybatis.org/mybatis-dynamic-sql/docs/select.html
 *
 * @author fp024
 */
@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
class BoardMapperTest {

  @Autowired private BoardMapper mapper;

  // order by를 사용한 페이지 쿼리의 코드화
  //     SELECT bno
  //          , title
  //          , content
  //          , writer
  //          , regdate AS regDate
  //          , updatedate AS updateDate
  //       FROM (SELECT rownum AS rn, bno, title, content, writer, regdate, updatedate
  //               FROM tbl_board
  //              WHERE rownum <= #{pageNum} * #{amount}
  //              ORDER BY bno DESC
  //              )
  //      WHERE rn > (#{pageNum} - 1) * #{amount}
  @Test
  void testGetListWithPaging() {
    DerivedColumn<Long> ROWNUM = DerivedColumn.of("ROWNUM");
    DerivedColumn<Long> rn = ROWNUM.as("rn");

    List<BoardVO> boardList =
        mapper.selectMany(
            SqlBuilder.select(BoardMapper.selectList)
                .from(
                    SqlBuilder.select(rn, bno, title, content, writer, regdate, updateDate)
                        .from(BoardVODynamicSqlSupport.boardVO)
                        .where(rn, SqlBuilder.isLessThanOrEqualTo(10L))
                        .orderBy(bno.descending()))
                // 여기에 위에서 만든 rn을 넣으면 ROWNOM으로 쿼리가 만들어진다.
                // 쿼리 모양을 완전히 동일하게 하려면 DerivedColumn.of("rn")으로 넣어야한다.
                .where(DerivedColumn.of("rn"), SqlBuilder.isGreaterThan(0L))
                .orderBy(bno.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3));

    boardList.forEach(b -> LOGGER.info("{}", b.toString()));
  }

  /*
   * 어떻게든 Hint를 쿼리문에 집어넣을 수는 있는데... 쉼표(,)를 무조건 붙이기 때문에
   * dummy 컬럼을 노출해줘야하는 문제가 있다.
   */
  // 만들고 싶은 쿼리
  //     SELECT bno
  //          , title
  //          , content
  //          , writer
  //          , regdate AS regDate
  //          , updatedate AS updateDate
  //       FROM (SELECT /*+ INDEX_DESC(tbl_board pk_board) */
  //                    rownum AS rn, bno, title, content, writer, regdate, updatedate
  //               FROM tbl_board
  //              WHERE rownum <= #{pageNum} * #{amount})
  //      WHERE rn > (#{pageNum} - 1) * #{amount}
  // 어쩔 수 없이 dummy가 붙은  쿼리 (select()에 Constant를 넣을 때, 쉼표를 자동으로 붙여서 어쩔 수 없다.)
  //     SELECT bno
  //          , title
  //          , content
  //          , writer
  //          , regdate AS regDate
  //          , updatedate AS updateDate
  //       FROM (SELECT /*+ INDEX_DESC(tbl_board pk_board) */ 'dummy',
  //               rownum AS rn, bno, title, content, writer, regdate, updatedate
  //               FROM tbl_board
  //              WHERE rownum <= #{pageNum} * #{amount})
  //      WHERE rn > (#{pageNum} - 1) * #{amount}
  @Test
  void testGetListWithPaging_Hint() {
    DerivedColumn<Long> ROWNUM = DerivedColumn.of("ROWNUM");
    DerivedColumn<Long> rn = ROWNUM.as("rn");

    Constant<String> hint = Constant.of("/*+ INDEX_DESC(tbl_board pk_board) */ 'dummy'");

    List<BoardVO> boardList =
        mapper.selectMany(
            SqlBuilder.select(BoardMapper.selectList)
                .from(
                    SqlBuilder.select(hint, rn, bno, title, content, writer, regdate, updateDate)
                        .from(BoardVODynamicSqlSupport.boardVO)
                        .where(rn, SqlBuilder.isLessThanOrEqualTo(10L)))
                .where(DerivedColumn.of("rn"), SqlBuilder.isGreaterThan(0L))
                .orderBy(bno.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3));

    boardList.forEach(b -> LOGGER.info("{}", b.toString()));
  }

  /**
   * 시퀀스를 generatorConfig.xml에서 정해주면 insert() 함수에 아래 어노테이션을 붙여줘서 실행하게되는데, 일부러 해당 값을 반환받지 않게 하기도
   * 애매하고, 굳이 안받는 메서드를 따로 만들 필요는 없을 것 같다., 필요시 호출 후 bno를 null로 바꿔주면 될 것 같다. @SelectKey(statement =
   * "SELECT seq_board.nextval FROM DUAL" , keyProperty = "record.bno", before = true, resultType =
   * Long.class)
   */
  @Test
  void testInsertSelectKey() {
    BoardVO board = new BoardVO();
    board.setTitle("새로 작성하는 글 select key");
    board.setContent("새로 작성하는 내용 select key");
    board.setWriter("newbie");

    // 테이블 기본값으로 SYSDATE로 처리되는 일자시간 컬럼
    board.setRegdate(null);
    board.setUpdateDate(null);

    mapper.insertSelective(board);
    LOGGER.info(board.toString());
  }

  /** selectByPrimaryKey 가 Optional<T> 로 반환해준다. */
  @Test
  void testRead() {
    // 존재하는 게시물 번호로 테스트
    Optional<BoardVO> board = mapper.selectByPrimaryKey(1L);
    LOGGER.info(board.get().toString());
  }

  @Test
  void testDelete() {
    LOGGER.info("DELETE COUNT: {}", mapper.deleteByPrimaryKey(3L));
  }

  @Test
  void testUpdate() {
    BoardVO board = new BoardVO();

    board.setBno(28L);
    board.setTitle("수정된 제목 - jex02");
    board.setContent("수정된 내용 - jex02");
    board.setWriter("user00 - jex02");
    board.setUpdateDate(LocalDateTime.now());

    // Selective로 끝나는 메서드를 사용하면, null로 설정된 값은 업데이트하지 않는다.
    int count = mapper.updateByPrimaryKeySelective(board);
    LOGGER.info("UPDATE COUNT: {}", count);
  }

  @Test
  void testUpdateWithDSL() {
    BoardVO board = new BoardVO();

    board.setBno(28L);
    board.setTitle("수정된 제목 - jex02 1");
    board.setContent("수정된 내용 - jex02 1");
    board.setWriter("user00 - jex02 1");
    board.setUpdateDate(LocalDateTime.now());

    UpdateStatementProvider updateStatement =
        SqlBuilder.update(BoardVODynamicSqlSupport.boardVO)
            .set(BoardVODynamicSqlSupport.title)
            .equalTo(board.getTitle())
            .set(BoardVODynamicSqlSupport.content)
            .equalTo(board.getContent())
            .set(BoardVODynamicSqlSupport.writer)
            .equalTo(board.getWriter())
            .set(BoardVODynamicSqlSupport.updateDate)
            .equalTo(board.getUpdateDate())
            .where(BoardVODynamicSqlSupport.bno, SqlBuilder.isEqualTo(board.getBno()))
            .build()
            .render(RenderingStrategies.MYBATIS3);

    // Selective로 끝나는 메서드를 사용하면, null로 설정된 값은 업데이트하지 않는다.
    int count = mapper.update(updateStatement);
    LOGGER.info("UPDATE COUNT: {}", count);
  }
}
