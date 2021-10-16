package org.fp024.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BoardVODynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BoardVO boardVO = new BoardVO();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> bno = boardVO.bno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> title = boardVO.title;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> content = boardVO.content;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> writer = boardVO.writer;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> regdate = boardVO.regdate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> updateDate = boardVO.updateDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BoardVO extends SqlTable {
        public final SqlColumn<Long> bno = column("BNO", JDBCType.BIGINT);

        public final SqlColumn<String> title = column("TITLE", JDBCType.VARCHAR);

        public final SqlColumn<String> content = column("CONTENT", JDBCType.VARCHAR);

        public final SqlColumn<String> writer = column("WRITER", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> regdate = column("REGDATE", JDBCType.DATE);

        public final SqlColumn<LocalDateTime> updateDate = column("UPDATEDATE", JDBCType.DATE);

        public BoardVO() {
            super("TBL_BOARD");
        }
    }
}