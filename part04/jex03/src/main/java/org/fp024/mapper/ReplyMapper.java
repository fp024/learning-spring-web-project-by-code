package org.fp024.mapper;

import static org.fp024.mapper.ReplyVODynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.fp024.domain.ReplyVO;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface ReplyMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(rno, bno, reply, replyer, replyDate, updateDate);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT seq_reply.nextval FROM DUAL", keyProperty="record.rno", before=true, resultType=Long.class)
    int insert(InsertStatementProvider<ReplyVO> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ReplyVOResult")
    Optional<ReplyVO> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ReplyVOResult", value = {
        @Result(column="RNO", property="rno", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="BNO", property="bno", jdbcType=JdbcType.NUMERIC),
        @Result(column="REPLY", property="reply", jdbcType=JdbcType.VARCHAR),
        @Result(column="REPLYER", property="replyer", jdbcType=JdbcType.VARCHAR),
        @Result(column="REPLYDATE", property="replyDate", jdbcType=JdbcType.DATE),
        @Result(column="UPDATEDATE", property="updateDate", jdbcType=JdbcType.DATE)
    })
    List<ReplyVO> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

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
        return delete(c -> 
            c.where(rno, isEqualTo(rno_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(ReplyVO record) {
        return MyBatis3Utils.insert(this::insert, record, replyVO, c ->
            c.map(rno).toProperty("rno")
            .map(bno).toProperty("bno")
            .map(reply).toProperty("reply")
            .map(replyer).toProperty("replyer")
            .map(replyDate).toProperty("replyDate")
            .map(updateDate).toProperty("updateDate")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(ReplyVO record) {
        return MyBatis3Utils.insert(this::insert, record, replyVO, c ->
            c.map(rno).toProperty("rno")
            .map(bno).toPropertyWhenPresent("bno", record::getBno)
            .map(reply).toPropertyWhenPresent("reply", record::getReply)
            .map(replyer).toPropertyWhenPresent("replyer", record::getReplyer)
            .map(replyDate).toPropertyWhenPresent("replyDate", record::getReplyDate)
            .map(updateDate).toPropertyWhenPresent("updateDate", record::getUpdateDate)
        );
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
        return selectOne(c ->
            c.where(rno, isEqualTo(rno_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, replyVO, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(ReplyVO record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(rno).equalTo(record::getRno)
                .set(bno).equalTo(record::getBno)
                .set(reply).equalTo(record::getReply)
                .set(replyer).equalTo(record::getReplyer)
                .set(replyDate).equalTo(record::getReplyDate)
                .set(updateDate).equalTo(record::getUpdateDate);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(ReplyVO record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(rno).equalToWhenPresent(record::getRno)
                .set(bno).equalToWhenPresent(record::getBno)
                .set(reply).equalToWhenPresent(record::getReply)
                .set(replyer).equalToWhenPresent(record::getReplyer)
                .set(replyDate).equalToWhenPresent(record::getReplyDate)
                .set(updateDate).equalToWhenPresent(record::getUpdateDate);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(ReplyVO record) {
        return update(c ->
            c.set(bno).equalTo(record::getBno)
            .set(reply).equalTo(record::getReply)
            .set(replyer).equalTo(record::getReplyer)
            .set(replyDate).equalTo(record::getReplyDate)
            .set(updateDate).equalTo(record::getUpdateDate)
            .where(rno, isEqualTo(record::getRno))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(ReplyVO record) {
        return update(c ->
            c.set(bno).equalToWhenPresent(record::getBno)
            .set(reply).equalToWhenPresent(record::getReply)
            .set(replyer).equalToWhenPresent(record::getReplyer)
            .set(replyDate).equalToWhenPresent(record::getReplyDate)
            .set(updateDate).equalToWhenPresent(record::getUpdateDate)
            .where(rno, isEqualTo(record::getRno))
        );
    }
}