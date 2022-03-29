package org.fp024.domain;

import javax.annotation.Generated;

public class Sample1 {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String col1;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getCol1() {
        return col1;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCol1(String col1) {
        this.col1 = col1 == null ? null : col1.trim();
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", col1=").append(col1);
        sb.append("]");
        return sb.toString();
    }
}