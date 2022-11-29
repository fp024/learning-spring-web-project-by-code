package org.fp024.mapper;

import static org.fp024.mapper.AuthVODynamicSqlSupport.auth;
import static org.fp024.mapper.AuthVODynamicSqlSupport.authVO;
import static org.fp024.mapper.AuthVODynamicSqlSupport.userId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.fp024.domain.AuthVO;
import org.fp024.typehandler.CustomEnumTypeHandler;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface AuthMapper
    extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<AuthVO>, CommonUpdateMapper {
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  BasicColumn[] selectList = BasicColumn.columnList(userId, auth);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @Results(
      id = "AuthVOResult",
      value = {
        @Result(column = "USERID", property = "userId", jdbcType = JdbcType.VARCHAR),
        @Result(
            column = "AUTH",
            property = "auth",
            typeHandler = CustomEnumTypeHandler.class,
            jdbcType = JdbcType.VARCHAR)
      })
  List<AuthVO> selectMany(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @ResultMap("AuthVOResult")
  Optional<AuthVO> selectOne(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default long count(CountDSLCompleter completer) {
    return MyBatis3Utils.countFrom(this::count, authVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int delete(DeleteDSLCompleter completer) {
    return MyBatis3Utils.deleteFrom(this::delete, authVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insert(AuthVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        authVO,
        c -> c.map(userId).toProperty("userId").map(auth).toProperty("auth"));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insertMultiple(Collection<AuthVO> records) {
    return MyBatis3Utils.insertMultiple(
        this::insertMultiple,
        records,
        authVO,
        c -> c.map(userId).toProperty("userId").map(auth).toProperty("auth"));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insertSelective(AuthVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        authVO,
        c ->
            c.map(userId)
                .toPropertyWhenPresent("userId", row::getUserId)
                .map(auth)
                .toPropertyWhenPresent("auth", row::getAuth));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default Optional<AuthVO> selectOne(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectOne(this::selectOne, selectList, authVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<AuthVO> select(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectList(this::selectMany, selectList, authVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<AuthVO> selectDistinct(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectDistinct(this::selectMany, selectList, authVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int update(UpdateDSLCompleter completer) {
    return MyBatis3Utils.update(this::update, authVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateAllColumns(AuthVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(userId).equalTo(row::getUserId).set(auth).equalTo(row::getAuth);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateSelectiveColumns(AuthVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(userId)
        .equalToWhenPresent(row::getUserId)
        .set(auth)
        .equalToWhenPresent(row::getAuth);
  }
}
