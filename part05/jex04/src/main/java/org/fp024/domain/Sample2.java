package org.fp024.domain;

import javax.annotation.Generated;

public class Sample2 {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String col2;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getCol2() {
        return col2;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCol2(String col2) {
        this.col2 = col2 == null ? null : col2.trim();
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", col2=").append(col2);
        sb.append("]");
        return sb.toString();
    }
}