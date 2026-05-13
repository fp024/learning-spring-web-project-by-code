package org.fp024.mapper.generated;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class BoardVODynamicSqlSupport {
    public static final BoardVO boardVO = new BoardVO();

    public static final SqlColumn<Long> bno = boardVO.bno;

    public static final SqlColumn<String> title = boardVO.title;

    public static final SqlColumn<String> content = boardVO.content;

    public static final SqlColumn<String> writer = boardVO.writer;

    public static final SqlColumn<LocalDateTime> regdate = boardVO.regdate;

    public static final SqlColumn<LocalDateTime> updateDate = boardVO.updateDate;

    public static final SqlColumn<Integer> replyCount = boardVO.replyCount;

    public static final class BoardVO extends AliasableSqlTable<BoardVO> {
        public final SqlColumn<Long> bno = column("BNO", JDBCType.BIGINT).withJavaProperty("bno");

        public final SqlColumn<String> title = column("TITLE", JDBCType.VARCHAR).withJavaProperty("title");

        public final SqlColumn<String> content = column("CONTENT", JDBCType.VARCHAR).withJavaProperty("content");

        public final SqlColumn<String> writer = column("WRITER", JDBCType.VARCHAR).withJavaProperty("writer");

        public final SqlColumn<LocalDateTime> regdate = column("REGDATE", JDBCType.DATE).withJavaProperty("regdate");

        public final SqlColumn<LocalDateTime> updateDate = column("UPDATEDATE", JDBCType.DATE).withJavaProperty("updateDate");

        public final SqlColumn<Integer> replyCount = column("REPLYCNT", JDBCType.BIGINT).withJavaProperty("replyCount");

        public BoardVO() {
            super("TBL_BOARD", BoardVO::new);
        }
    }
}