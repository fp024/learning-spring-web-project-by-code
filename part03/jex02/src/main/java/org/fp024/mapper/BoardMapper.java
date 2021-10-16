package org.fp024.mapper;

import static org.fp024.mapper.BoardVODynamicSqlSupport.*;
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
import org.fp024.domain.BoardVO;
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
public interface BoardMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(bno, title, content, writer, regdate, updateDate);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT seq_board.nextval FROM DUAL", keyProperty="record.bno", before=true, resultType=Long.class)
    int insert(InsertStatementProvider<BoardVO> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BoardVOResult")
    Optional<BoardVO> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BoardVOResult", value = {
        @Result(column="BNO", property="bno", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TITLE", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="CONTENT", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="WRITER", property="writer", jdbcType=JdbcType.VARCHAR),
        @Result(column="REGDATE", property="regdate", jdbcType=JdbcType.DATE),
        @Result(column="UPDATEDATE", property="updateDate", jdbcType=JdbcType.DATE)
    })
    List<BoardVO> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

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
        return delete(c -> 
            c.where(bno, isEqualTo(bno_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BoardVO record) {
        return MyBatis3Utils.insert(this::insert, record, boardVO, c ->
            c.map(bno).toProperty("bno")
            .map(title).toProperty("title")
            .map(content).toProperty("content")
            .map(writer).toProperty("writer")
            .map(regdate).toProperty("regdate")
            .map(updateDate).toProperty("updateDate")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BoardVO record) {
        return MyBatis3Utils.insert(this::insert, record, boardVO, c ->
            c.map(bno).toProperty("bno")
            .map(title).toPropertyWhenPresent("title", record::getTitle)
            .map(content).toPropertyWhenPresent("content", record::getContent)
            .map(writer).toPropertyWhenPresent("writer", record::getWriter)
            .map(regdate).toPropertyWhenPresent("regdate", record::getRegdate)
            .map(updateDate).toPropertyWhenPresent("updateDate", record::getUpdateDate)
        );
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
        return selectOne(c ->
            c.where(bno, isEqualTo(bno_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, boardVO, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(BoardVO record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(bno).equalTo(record::getBno)
                .set(title).equalTo(record::getTitle)
                .set(content).equalTo(record::getContent)
                .set(writer).equalTo(record::getWriter)
                .set(regdate).equalTo(record::getRegdate)
                .set(updateDate).equalTo(record::getUpdateDate);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(BoardVO record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(bno).equalToWhenPresent(record::getBno)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(content).equalToWhenPresent(record::getContent)
                .set(writer).equalToWhenPresent(record::getWriter)
                .set(regdate).equalToWhenPresent(record::getRegdate)
                .set(updateDate).equalToWhenPresent(record::getUpdateDate);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BoardVO record) {
        return update(c ->
            c.set(title).equalTo(record::getTitle)
            .set(content).equalTo(record::getContent)
            .set(writer).equalTo(record::getWriter)
            .set(regdate).equalTo(record::getRegdate)
            .set(updateDate).equalTo(record::getUpdateDate)
            .where(bno, isEqualTo(record::getBno))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BoardVO record) {
        return update(c ->
            c.set(title).equalToWhenPresent(record::getTitle)
            .set(content).equalToWhenPresent(record::getContent)
            .set(writer).equalToWhenPresent(record::getWriter)
            .set(regdate).equalToWhenPresent(record::getRegdate)
            .set(updateDate).equalToWhenPresent(record::getUpdateDate)
            .where(bno, isEqualTo(record::getBno))
        );
    }
}