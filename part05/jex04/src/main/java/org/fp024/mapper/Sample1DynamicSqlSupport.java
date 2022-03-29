package org.fp024.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class Sample1DynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final Sample1 sample1 = new Sample1();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> col1 = sample1.col1;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class Sample1 extends AliasableSqlTable<Sample1> {
        public final SqlColumn<String> col1 = column("COL1", JDBCType.VARCHAR);

        public Sample1() {
            super("TBL_SAMPLE1", Sample1::new);
        }
    }
}