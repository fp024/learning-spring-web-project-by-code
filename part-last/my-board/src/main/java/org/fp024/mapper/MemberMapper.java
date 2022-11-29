package org.fp024.mapper;

import static org.fp024.mapper.MemberVODynamicSqlSupport.enabled;
import static org.fp024.mapper.MemberVODynamicSqlSupport.memberVO;
import static org.fp024.mapper.MemberVODynamicSqlSupport.registerDate;
import static org.fp024.mapper.MemberVODynamicSqlSupport.updateDate;
import static org.fp024.mapper.MemberVODynamicSqlSupport.userId;
import static org.fp024.mapper.MemberVODynamicSqlSupport.userName;
import static org.fp024.mapper.MemberVODynamicSqlSupport.userPassword;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

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
import org.fp024.domain.MemberVO;
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
public interface MemberMapper
    extends CommonCountMapper,
        CommonDeleteMapper,
        CommonInsertMapper<MemberVO>,
        CommonUpdateMapper {
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  BasicColumn[] selectList =
      BasicColumn.columnList(userId, userPassword, userName, registerDate, updateDate, enabled);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @Results(
      id = "MemberVOResult",
      value = {
        @Result(column = "USERID", property = "userId", jdbcType = JdbcType.VARCHAR, id = true),
        @Result(column = "USERPW", property = "userPassword", jdbcType = JdbcType.VARCHAR),
        @Result(column = "USERNAME", property = "userName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "REGDATE", property = "registerDate", jdbcType = JdbcType.DATE),
        @Result(column = "UPDATEDATE", property = "updateDate", jdbcType = JdbcType.DATE),
        @Result(
            column = "ENABLED",
            property = "enabled",
            typeHandler = CustomEnumTypeHandler.class,
            jdbcType = JdbcType.CHAR)
      })
  List<MemberVO> selectMany(SelectStatementProvider selectStatement);

  /**
   * 회원과 권한이 1:N이여서 ResultMap을 꼭 구성해야했따. Mapper XML에 정의했다. <br>
   * 가이드에서도 ResultMap을 사용하라고 했다. <br>
   * 기본 쿼리문에서 없으면 없는대로 null로 들어가니 메서드를 따로 만들 필요는 없고 selectOne에만 추가해주면 되겠다.<br>
   * https://mybatis.org/mybatis-dynamic-sql/docs/select.html#xml-mapper-for-join-statements
   */
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @ResultMap("MemberResultMap")
  Optional<MemberVO> selectOne(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default long count(CountDSLCompleter completer) {
    return MyBatis3Utils.countFrom(this::count, memberVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int delete(DeleteDSLCompleter completer) {
    return MyBatis3Utils.deleteFrom(this::delete, memberVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int deleteByPrimaryKey(String userId_) {
    return delete(c -> c.where(userId, isEqualTo(userId_)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insert(MemberVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        memberVO,
        c ->
            c.map(userId)
                .toProperty("userId")
                .map(userPassword)
                .toProperty("userPassword")
                .map(userName)
                .toProperty("userName")
                .map(registerDate)
                .toProperty("registerDate")
                .map(updateDate)
                .toProperty("updateDate")
                .map(enabled)
                .toProperty("enabled"));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insertMultiple(Collection<MemberVO> records) {
    return MyBatis3Utils.insertMultiple(
        this::insertMultiple,
        records,
        memberVO,
        c ->
            c.map(userId)
                .toProperty("userId")
                .map(userPassword)
                .toProperty("userPassword")
                .map(userName)
                .toProperty("userName")
                .map(registerDate)
                .toProperty("registerDate")
                .map(updateDate)
                .toProperty("updateDate")
                .map(enabled)
                .toProperty("enabled"));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insertSelective(MemberVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        memberVO,
        c ->
            c.map(userId)
                .toPropertyWhenPresent("userId", row::getUserId)
                .map(userPassword)
                .toPropertyWhenPresent("userPassword", row::getUserPassword)
                .map(userName)
                .toPropertyWhenPresent("userName", row::getUserName)
                .map(registerDate)
                .toPropertyWhenPresent("registerDate", row::getRegisterDate)
                .map(updateDate)
                .toPropertyWhenPresent("updateDate", row::getUpdateDate)
                .map(enabled)
                .toPropertyWhenPresent("enabled", row::getEnabled));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @ResultMap("MemberResultMap")
  default Optional<MemberVO> selectOne(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectOne(this::selectOne, selectList, memberVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<MemberVO> select(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectList(this::selectMany, selectList, memberVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<MemberVO> selectDistinct(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectDistinct(this::selectMany, selectList, memberVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default Optional<MemberVO> selectByPrimaryKey(String userId_) {
    return selectOne(c -> c.where(userId, isEqualTo(userId_)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int update(UpdateDSLCompleter completer) {
    return MyBatis3Utils.update(this::update, memberVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateAllColumns(MemberVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(userId)
        .equalTo(row::getUserId)
        .set(userPassword)
        .equalTo(row::getUserPassword)
        .set(userName)
        .equalTo(row::getUserName)
        .set(registerDate)
        .equalTo(row::getRegisterDate)
        .set(updateDate)
        .equalTo(row::getUpdateDate)
        .set(enabled)
        .equalTo(row::getEnabled);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateSelectiveColumns(MemberVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(userId)
        .equalToWhenPresent(row::getUserId)
        .set(userPassword)
        .equalToWhenPresent(row::getUserPassword)
        .set(userName)
        .equalToWhenPresent(row::getUserName)
        .set(registerDate)
        .equalToWhenPresent(row::getRegisterDate)
        .set(updateDate)
        .equalToWhenPresent(row::getUpdateDate)
        .set(enabled)
        .equalToWhenPresent(row::getEnabled);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int updateByPrimaryKey(MemberVO row) {
    return update(
        c ->
            c.set(userPassword)
                .equalTo(row::getUserPassword)
                .set(userName)
                .equalTo(row::getUserName)
                .set(registerDate)
                .equalTo(row::getRegisterDate)
                .set(updateDate)
                .equalTo(row::getUpdateDate)
                .set(enabled)
                .equalTo(row::getEnabled)
                .where(userId, isEqualTo(row::getUserId)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int updateByPrimaryKeySelective(MemberVO row) {
    return update(
        c ->
            c.set(userPassword)
                .equalToWhenPresent(row::getUserPassword)
                .set(userName)
                .equalToWhenPresent(row::getUserName)
                .set(registerDate)
                .equalToWhenPresent(row::getRegisterDate)
                .set(updateDate)
                .equalToWhenPresent(row::getUpdateDate)
                .set(enabled)
                .equalToWhenPresent(row::getEnabled)
                .where(userId, isEqualTo(row::getUserId)));
  }
}
