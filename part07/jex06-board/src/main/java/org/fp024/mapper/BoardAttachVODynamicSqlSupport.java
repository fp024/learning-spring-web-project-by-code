package org.fp024.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.fp024.domain.FileType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class BoardAttachVODynamicSqlSupport {
  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final BoardAttachVO boardAttachVO = new BoardAttachVO();

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<String> uuid = boardAttachVO.uuid;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<String> uploadPath = boardAttachVO.uploadPath;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<String> fileName = boardAttachVO.fileName;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<FileType> fileType = boardAttachVO.fileType;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final SqlColumn<Long> bno = boardAttachVO.bno;

  @Generated("org.mybatis.generator.api.MyBatisGenerator")
  public static final class BoardAttachVO extends AliasableSqlTable<BoardAttachVO> {
    public final SqlColumn<String> uuid = column("UUID", JDBCType.VARCHAR);

    public final SqlColumn<String> uploadPath = column("UPLOADPATH", JDBCType.VARCHAR);

    public final SqlColumn<String> fileName = column("FILENAME", JDBCType.VARCHAR);

    public final SqlColumn<FileType> fileType =
        column("FILETYPE", JDBCType.CHAR, "org.fp024.typehandler.FileTypeEnumHandler");

    public final SqlColumn<Long> bno = column("BNO", JDBCType.BIGINT);

    public BoardAttachVO() {
      super("TBL_ATTACH", BoardAttachVO::new);
    }
  }
}
