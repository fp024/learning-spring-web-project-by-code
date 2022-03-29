package org.fp024.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class Sample2DynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final Sample2 sample2 = new Sample2();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> col2 = sample2.col2;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class Sample2 extends AliasableSqlTable<Sample2> {
        public final SqlColumn<String> col2 = column("COL2", JDBCType.VARCHAR);

        public Sample2() {
            super("TBL_SAMPLE2", Sample2::new);
        }
    }
}