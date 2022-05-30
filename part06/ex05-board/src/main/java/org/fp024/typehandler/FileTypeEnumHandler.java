package org.fp024.typehandler;

import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.fp024.domain.FileType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * FileType Enum을 처리하는 헨들러
 *
 * <p>DB에 저장할 때 Enum의 code 값으로 저장하고, 불러올 때는 코드값을 사용해 Eunm으로 변환하여 가져온다.
 */
@MappedTypes(FileType.class)
public class FileTypeEnumHandler extends EnumTypeHandler<FileType> {
  public FileTypeEnumHandler(Class<FileType> type) {
    super(type);
  }

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int i, FileType parameter, JdbcType jdbcType) throws SQLException {
    if (jdbcType == null) {
      ps.setString(i, parameter.getCode());
    } else {
      ps.setObject(i, parameter.getCode(), jdbcType.TYPE_CODE);
    }
  }

  @Override
  public FileType getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String s = rs.getString(columnName);
    return s == null ? null : FileType.valueByCode(s);
  }

  @Override
  public FileType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String s = rs.getString(columnIndex);
    return s == null ? null : FileType.valueByCode(s);
  }

  @Override
  public FileType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String s = cs.getString(columnIndex);
    return s == null ? null : FileType.valueByCode(s);
  }
}
