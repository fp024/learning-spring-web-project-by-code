package org.fp024.mapper;

import org.fp024.type.EnabledType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;

public final class MemberVODynamicSqlSupport {
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final MemberVO memberVO = new MemberVO();

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<String> userId = memberVO.userId;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<String> userPassword = memberVO.userPassword;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<String> userName = memberVO.userName;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<LocalDateTime> registerDate = memberVO.registerDate;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<LocalDateTime> updateDate = memberVO.updateDate;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<EnabledType> enabled = memberVO.enabled;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final class MemberVO extends AliasableSqlTable<MemberVO> {
    public final SqlColumn<String> userId = column("USERID", JDBCType.VARCHAR);

    public final SqlColumn<String> userPassword = column("USERPW", JDBCType.VARCHAR);

    public final SqlColumn<String> userName = column("USERNAME", JDBCType.VARCHAR);

    public final SqlColumn<LocalDateTime> registerDate = column("REGDATE", JDBCType.DATE);

    public final SqlColumn<LocalDateTime> updateDate = column("UPDATEDATE", JDBCType.DATE);

    public final SqlColumn<EnabledType> enabled =
        column("ENABLED", JDBCType.CHAR, "org.fp024.typehandler.CustomEnumTypeHandler");

    public MemberVO() {
      super("TBL_MEMBER", MemberVO::new);
    }
  }
}
