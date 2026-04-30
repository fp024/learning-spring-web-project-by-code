package org.fp024.mapper.generated;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class ReplyVODynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final ReplyVO replyVO = new ReplyVO();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> rno = replyVO.rno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> bno = replyVO.bno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> reply = replyVO.reply;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> replyer = replyVO.replyer;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> replyDate = replyVO.replyDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> updateDate = replyVO.updateDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class ReplyVO extends AliasableSqlTable<ReplyVO> {
        public final SqlColumn<Long> rno = column("RNO", JDBCType.NUMERIC);

        public final SqlColumn<Long> bno = column("BNO", JDBCType.NUMERIC);

        public final SqlColumn<String> reply = column("REPLY", JDBCType.VARCHAR);

        public final SqlColumn<String> replyer = column("REPLYER", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> replyDate = column("REPLYDATE", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> updateDate = column("UPDATEDATE", JDBCType.TIMESTAMP);

        public ReplyVO() {
            super("TBL_REPLY", ReplyVO::new);
        }
    }
}