package org.fp024.mapper;

import org.fp024.type.MemberAuthType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import javax.annotation.Generated;
import java.sql.JDBCType;

public final class AuthVODynamicSqlSupport {
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final AuthVO authVO = new AuthVO();

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<String> userId = authVO.userId;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<MemberAuthType> auth = authVO.auth;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final class AuthVO extends AliasableSqlTable<AuthVO> {
    public final SqlColumn<String> userId = column("USERID", JDBCType.VARCHAR);

    public final SqlColumn<MemberAuthType> auth =
        column("AUTH", JDBCType.VARCHAR, "org.fp024.typehandler.CustomEnumTypeHandler");

    public AuthVO() {
      super("TBL_MEMBER_AUTH", AuthVO::new);
    }
  }
}
