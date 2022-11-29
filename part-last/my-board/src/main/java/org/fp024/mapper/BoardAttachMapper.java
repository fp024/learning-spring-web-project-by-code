package org.fp024.mapper;

import static org.fp024.mapper.BoardAttachVODynamicSqlSupport.bno;
import static org.fp024.mapper.BoardAttachVODynamicSqlSupport.boardAttachVO;
import static org.fp024.mapper.BoardAttachVODynamicSqlSupport.fileName;
import static org.fp024.mapper.BoardAttachVODynamicSqlSupport.fileType;
import static org.fp024.mapper.BoardAttachVODynamicSqlSupport.uploadPath;
import static org.fp024.mapper.BoardAttachVODynamicSqlSupport.uuid;
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
import org.fp024.domain.BoardAttachVO;
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
public interface BoardAttachMapper
    extends CommonCountMapper,
        CommonDeleteMapper,
        CommonInsertMapper<BoardAttachVO>,
        CommonUpdateMapper {
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  BasicColumn[] selectList = BasicColumn.columnList(uuid, uploadPath, fileName, fileType, bno);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @Results(
      id = "BoardAttachVOResult",
      value = {
        @Result(column = "UUID", property = "uuid", jdbcType = JdbcType.VARCHAR, id = true),
        @Result(column = "UPLOADPATH", property = "uploadPath", jdbcType = JdbcType.VARCHAR),
        @Result(column = "FILENAME", property = "fileName", jdbcType = JdbcType.VARCHAR),
        @Result(
            column = "FILETYPE",
            property = "fileType",
            typeHandler = CustomEnumTypeHandler.class,
            jdbcType = JdbcType.CHAR),
        @Result(column = "BNO", property = "bno", jdbcType = JdbcType.BIGINT)
      })
  List<BoardAttachVO> selectMany(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @ResultMap("BoardAttachVOResult")
  Optional<BoardAttachVO> selectOne(SelectStatementProvider selectStatement);

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default long count(CountDSLCompleter completer) {
    return MyBatis3Utils.countFrom(this::count, boardAttachVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int delete(DeleteDSLCompleter completer) {
    return MyBatis3Utils.deleteFrom(this::delete, boardAttachVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int deleteByPrimaryKey(String uuid_) {
    return delete(c -> c.where(uuid, isEqualTo(uuid_)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insert(BoardAttachVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        boardAttachVO,
        c ->
            c.map(uuid)
                .toProperty("uuid")
                .map(uploadPath)
                .toProperty("uploadPath")
                .map(fileName)
                .toProperty("fileName")
                .map(fileType)
                .toProperty("fileType")
                .map(bno)
                .toProperty("bno"));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insertMultiple(Collection<BoardAttachVO> records) {
    return MyBatis3Utils.insertMultiple(
        this::insertMultiple,
        records,
        boardAttachVO,
        c ->
            c.map(uuid)
                .toProperty("uuid")
                .map(uploadPath)
                .toProperty("uploadPath")
                .map(fileName)
                .toProperty("fileName")
                .map(fileType)
                .toProperty("fileType")
                .map(bno)
                .toProperty("bno"));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int insertSelective(BoardAttachVO row) {
    return MyBatis3Utils.insert(
        this::insert,
        row,
        boardAttachVO,
        c ->
            c.map(uuid)
                .toPropertyWhenPresent("uuid", row::getUuid)
                .map(uploadPath)
                .toPropertyWhenPresent("uploadPath", row::getUploadPath)
                .map(fileName)
                .toPropertyWhenPresent("fileName", row::getFileName)
                .map(fileType)
                .toPropertyWhenPresent("fileType", row::getFileType)
                .map(bno)
                .toPropertyWhenPresent("bno", row::getBno));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default Optional<BoardAttachVO> selectOne(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectOne(this::selectOne, selectList, boardAttachVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<BoardAttachVO> select(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectList(this::selectMany, selectList, boardAttachVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default List<BoardAttachVO> selectDistinct(SelectDSLCompleter completer) {
    return MyBatis3Utils.selectDistinct(this::selectMany, selectList, boardAttachVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default Optional<BoardAttachVO> selectByPrimaryKey(String uuid_) {
    return selectOne(c -> c.where(uuid, isEqualTo(uuid_)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int update(UpdateDSLCompleter completer) {
    return MyBatis3Utils.update(this::update, boardAttachVO, completer);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateAllColumns(BoardAttachVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(uuid)
        .equalTo(row::getUuid)
        .set(uploadPath)
        .equalTo(row::getUploadPath)
        .set(fileName)
        .equalTo(row::getFileName)
        .set(fileType)
        .equalTo(row::getFileType)
        .set(bno)
        .equalTo(row::getBno);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  static UpdateDSL<UpdateModel> updateSelectiveColumns(
      BoardAttachVO row, UpdateDSL<UpdateModel> dsl) {
    return dsl.set(uuid)
        .equalToWhenPresent(row::getUuid)
        .set(uploadPath)
        .equalToWhenPresent(row::getUploadPath)
        .set(fileName)
        .equalToWhenPresent(row::getFileName)
        .set(fileType)
        .equalToWhenPresent(row::getFileType)
        .set(bno)
        .equalToWhenPresent(row::getBno);
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int updateByPrimaryKey(BoardAttachVO row) {
    return update(
        c ->
            c.set(uploadPath)
                .equalTo(row::getUploadPath)
                .set(fileName)
                .equalTo(row::getFileName)
                .set(fileType)
                .equalTo(row::getFileType)
                .set(bno)
                .equalTo(row::getBno)
                .where(uuid, isEqualTo(row::getUuid)));
  }

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  default int updateByPrimaryKeySelective(BoardAttachVO row) {
    return update(
        c ->
            c.set(uploadPath)
                .equalToWhenPresent(row::getUploadPath)
                .set(fileName)
                .equalToWhenPresent(row::getFileName)
                .set(fileType)
                .equalToWhenPresent(row::getFileType)
                .set(bno)
                .equalToWhenPresent(row::getBno)
                .where(uuid, isEqualTo(row::getUuid)));
  }
}
