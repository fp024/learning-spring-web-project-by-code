package org.fp024.mapper;

import static org.fp024.mapper.Sample1DynamicSqlSupport.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.fp024.domain.Sample1;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.dsl.CountDSLCompleter;
import org.mybatis.dynamic.sql.dsl.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.dsl.SelectDSLCompleter;
import org.mybatis.dynamic.sql.dsl.UpdateDSL;
import org.mybatis.dynamic.sql.dsl.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface Sample1Mapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<Sample1>, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(col1);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="Sample1Result", value = {
        @Result(column="COL1", property="col1", jdbcType=JdbcType.VARCHAR)
    })
    List<Sample1> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("Sample1Result")
    Optional<Sample1> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, sample1, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, sample1, completer);
    }

    default int insert(Sample1 row) {
        return MyBatis3Utils.insert(this::insert, row, sample1, c ->
            c.withMappedColumn(col1)
        );
    }

    default int insertMultiple(Collection<Sample1> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, sample1, c ->
            c.withMappedColumn(col1)
        );
    }

    default int insertSelective(Sample1 row) {
        return MyBatis3Utils.insert(this::insert, row, sample1, c ->
            c.withMappedColumnWhenPresent(col1, row::getCol1)
        );
    }

    default Optional<Sample1> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, sample1, completer);
    }

    default List<Sample1> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, sample1, completer);
    }

    default List<Sample1> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, sample1, completer);
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, sample1, completer);
    }

    static UpdateDSL updateAllColumns(Sample1 row, UpdateDSL dsl) {
        return dsl.set(col1).equalTo(row::getCol1);
    }

    static UpdateDSL updateSelectiveColumns(Sample1 row, UpdateDSL dsl) {
        return dsl.set(col1).equalToWhenPresent(row::getCol1);
    }
}