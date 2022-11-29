package org.fp024.mapper;

import static org.fp024.mapper.BoardVODynamicSqlSupport.bno;
import static org.fp024.mapper.BoardVODynamicSqlSupport.boardVO;
import static org.fp024.mapper.BoardVODynamicSqlSupport.content;
import static org.fp024.mapper.BoardVODynamicSqlSupport.regdate;
import static org.fp024.mapper.BoardVODynamicSqlSupport.replyCount;
import static org.fp024.mapper.BoardVODynamicSqlSupport.title;
import static org.fp024.mapper.BoardVODynamicSqlSupport.updateDate;
import static org.fp024.mapper.BoardVODynamicSqlSupport.writer;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.fp024.domain.BoardVO;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface BoardMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  BasicColumn[] selectList =
      BasicColumn.columnList(bno, title, content, writer, regdate, updateDate, replyCount);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
  @SelectKey(
      statement = "SELECT seq_board.nextval FROM DUAL",
      keyProperty = "row.bno",
      before = true,
      resultType = Long.class)
  int insert(InsertStatementProvider<BoardVO> insertStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @Results(
      id = "BoardVOResult",
      value = {
        @Result(column = "BNO", property = "bno", jdbcType = JdbcType.BIGINT, id = true),
        @Result(column = "TITLE", property = "title", jdbcType = JdbcType.VARCHAR),
        @Result(column = "CONTENT", property = "content", jdbcType = JdbcType.VARCHAR),
        @Result(column = "WRITER", property = "writer", jdbcType = JdbcType.VARCHAR),
        @Result(column = "REGDATE", property = "regdate", jdbcType = JdbcType.DATE),
        @Result(column = "UPDATEDATE", property = "updateDate", jdbcType = JdbcType.DATE),
        @Result(column = "REPLYCNT", property = "replyCount", jdbcType = JdbcType.BIGINT)
      })
  List<BoardVO> selectMany(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @ResultMap("BoardVOResult")
  Optional<BoardVO> selectOne(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default long count(CountDSLCompleter completer) {
    return MyBatis3Utils.countFrom(this::count, boardVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int delete(DeleteDSLCompleter completer) {
    return MyBatis3Utils.deleteFrom(this::delete, boardVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int deleteByPrimaryKey(Long bno_) {
    return delete(c -> c.where(bno, isEqualTo(bno_)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insert(BoardVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        boardVO,
        c ->
            c.map(bno)
                .toProperty("bno")
                .map(title)
                .toProperty("title")
                .map(content)
                .toProperty("content")
                .map(writer)
                .toProperty("writer")
                .map(regdate)
                .toProperty("regdate")
                .map(updateDate)
                .toProperty("updateDate")
                .map(replyCount)
                .toProperty("replyCount"));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insertSelective(BoardVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        boardVO,
        c ->
            c.map(bno)
                .toProperty("bno")
                .map(title)
                .toPropertyWhenPresent("title", row::getTitle)
                .map(content)
                .toPropertyWhenPresent("content", row::getContent)
                .map(writer)
                .toPropertyWhenPresent("writer", row::getWriter)
                .map(regdate)
                .toPropertyWhenPresent("regdate", row::getRegdate)
                .map(updateDate)
                .toPropertyWhenPresent("updateDate", row::getUpdateDate)
                .map(replyCount)
                .toPropertyWhenPresent("replyCount", row::getReplyCount));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default Optional<BoardVO> selectOne(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectOne(this::selectOne, selectList, boardVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<BoardVO> select(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectList(this::selectMany, selectList, boardVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<BoardVO> selectDistinct(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectDistinct(this::selectMany, selectList, boardVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default Optional<BoardVO> selectByPrimaryKey(Long bno_) {
    return selectOne(c -> c.where(bno, isEqualTo(bno_)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int update(UpdateDSLCompleter completer) {
    return MyBatis3Utils.update(this::update, boardVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateAllColumns(BoardVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(bno)
        .equalTo(row::getBno)
        .set(title)
        .equalTo(row::getTitle)
        .set(content)
        .equalTo(row::getContent)
        .set(writer)
        .equalTo(row::getWriter)
        .set(regdate)
        .equalTo(row::getRegdate)
        .set(updateDate)
        .equalTo(row::getUpdateDate)
        .set(replyCount)
        .equalTo(row::getReplyCount);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateSelectiveColumns(BoardVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(bno)
        .equalToWhenPresent(row::getBno)
        .set(title)
        .equalToWhenPresent(row::getTitle)
        .set(content)
        .equalToWhenPresent(row::getContent)
        .set(writer)
        .equalToWhenPresent(row::getWriter)
        .set(regdate)
        .equalToWhenPresent(row::getRegdate)
        .set(updateDate)
        .equalToWhenPresent(row::getUpdateDate)
        .set(replyCount)
        .equalToWhenPresent(row::getReplyCount);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int updateByPrimaryKey(BoardVO row) {
    return update(
        c ->
            c.set(title)
                .equalTo(row::getTitle)
                .set(content)
                .equalTo(row::getContent)
                .set(writer)
                .equalTo(row::getWriter)
                .set(regdate)
                .equalTo(row::getRegdate)
                .set(updateDate)
                .equalTo(row::getUpdateDate)
                .set(replyCount)
                .equalTo(row::getReplyCount)
                .where(bno, isEqualTo(row::getBno)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int updateByPrimaryKeySelective(BoardVO row) {
    return update(
        c ->
            c.set(title)
                .equalToWhenPresent(row::getTitle)
                .set(content)
                .equalToWhenPresent(row::getContent)
                .set(writer)
                .equalToWhenPresent(row::getWriter)
                .set(regdate)
                .equalToWhenPresent(row::getRegdate)
                .set(updateDate)
                .equalToWhenPresent(row::getUpdateDate)
                .set(replyCount)
                .equalToWhenPresent(row::getReplyCount)
                .where(bno, isEqualTo(row::getBno)));
  }
}
