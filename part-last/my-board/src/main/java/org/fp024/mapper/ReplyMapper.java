package org.fp024.mapper;

import static org.fp024.mapper.ReplyVODynamicSqlSupport.bno;
import static org.fp024.mapper.ReplyVODynamicSqlSupport.reply;
import static org.fp024.mapper.ReplyVODynamicSqlSupport.replyDate;
import static org.fp024.mapper.ReplyVODynamicSqlSupport.replyVO;
import static org.fp024.mapper.ReplyVODynamicSqlSupport.replyer;
import static org.fp024.mapper.ReplyVODynamicSqlSupport.rno;
import static org.fp024.mapper.ReplyVODynamicSqlSupport.updateDate;
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
import org.fp024.domain.ReplyVO;
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
public interface ReplyMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  BasicColumn[] selectList =
      BasicColumn.columnList(rno, bno, reply, replyer, replyDate, updateDate);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
  @SelectKey(
      statement = "SELECT seq_reply.nextval FROM DUAL",
      keyProperty = "row.rno",
      before = true,
      resultType = Long.class)
  int insert(InsertStatementProvider<ReplyVO> insertStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @Results(
      id = "ReplyVOResult",
      value = {
        @Result(column = "RNO", property = "rno", jdbcType = JdbcType.BIGINT, id = true),
        @Result(column = "BNO", property = "bno", jdbcType = JdbcType.NUMERIC),
        @Result(column = "REPLY", property = "reply", jdbcType = JdbcType.VARCHAR),
        @Result(column = "REPLYER", property = "replyer", jdbcType = JdbcType.VARCHAR),
        @Result(column = "REPLYDATE", property = "replyDate", jdbcType = JdbcType.DATE),
        @Result(column = "UPDATEDATE", property = "updateDate", jdbcType = JdbcType.DATE)
      })
  List<ReplyVO> selectMany(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @ResultMap("ReplyVOResult")
  Optional<ReplyVO> selectOne(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default long count(CountDSLCompleter completer) {
    return MyBatis3Utils.countFrom(this::count, replyVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int delete(DeleteDSLCompleter completer) {
    return MyBatis3Utils.deleteFrom(this::delete, replyVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int deleteByPrimaryKey(Long rno_) {
    return delete(c -> c.where(rno, isEqualTo(rno_)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insert(ReplyVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        replyVO,
        c ->
            c.map(rno)
                .toProperty("rno")
                .map(bno)
                .toProperty("bno")
                .map(reply)
                .toProperty("reply")
                .map(replyer)
                .toProperty("replyer")
                .map(replyDate)
                .toProperty("replyDate")
                .map(updateDate)
                .toProperty("updateDate"));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insertSelective(ReplyVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        replyVO,
        c ->
            c.map(rno)
                .toProperty("rno")
                .map(bno)
                .toPropertyWhenPresent("bno", row::getBno)
                .map(reply)
                .toPropertyWhenPresent("reply", row::getReply)
                .map(replyer)
                .toPropertyWhenPresent("replyer", row::getReplyer)
                .map(replyDate)
                .toPropertyWhenPresent("replyDate", row::getReplyDate)
                .map(updateDate)
                .toPropertyWhenPresent("updateDate", row::getUpdateDate));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default Optional<ReplyVO> selectOne(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectOne(this::selectOne, selectList, replyVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<ReplyVO> select(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectList(this::selectMany, selectList, replyVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<ReplyVO> selectDistinct(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectDistinct(this::selectMany, selectList, replyVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default Optional<ReplyVO> selectByPrimaryKey(Long rno_) {
    return selectOne(c -> c.where(rno, isEqualTo(rno_)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int update(UpdateDSLCompleter completer) {
    return MyBatis3Utils.update(this::update, replyVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateAllColumns(ReplyVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(rno)
        .equalTo(row::getRno)
        .set(bno)
        .equalTo(row::getBno)
        .set(reply)
        .equalTo(row::getReply)
        .set(replyer)
        .equalTo(row::getReplyer)
        .set(replyDate)
        .equalTo(row::getReplyDate)
        .set(updateDate)
        .equalTo(row::getUpdateDate);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateSelectiveColumns(ReplyVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(rno)
        .equalToWhenPresent(row::getRno)
        .set(bno)
        .equalToWhenPresent(row::getBno)
        .set(reply)
        .equalToWhenPresent(row::getReply)
        .set(replyer)
        .equalToWhenPresent(row::getReplyer)
        .set(replyDate)
        .equalToWhenPresent(row::getReplyDate)
        .set(updateDate)
        .equalToWhenPresent(row::getUpdateDate);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int updateByPrimaryKey(ReplyVO row) {
    return update(
        c ->
            c.set(bno)
                .equalTo(row::getBno)
                .set(reply)
                .equalTo(row::getReply)
                .set(replyer)
                .equalTo(row::getReplyer)
                .set(replyDate)
                .equalTo(row::getReplyDate)
                .set(updateDate)
                .equalTo(row::getUpdateDate)
                .where(rno, isEqualTo(row::getRno)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int updateByPrimaryKeySelective(ReplyVO row) {
    return update(
        c ->
            c.set(bno)
                .equalToWhenPresent(row::getBno)
                .set(reply)
                .equalToWhenPresent(row::getReply)
                .set(replyer)
                .equalToWhenPresent(row::getReplyer)
                .set(replyDate)
                .equalToWhenPresent(row::getReplyDate)
                .set(updateDate)
                .equalToWhenPresent(row::getUpdateDate)
                .where(rno, isEqualTo(row::getRno)));
  }
}
