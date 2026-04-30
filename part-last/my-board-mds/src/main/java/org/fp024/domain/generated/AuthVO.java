package org.fp024.domain.generated;

import jakarta.annotation.Generated;
import org.fp024.domain.MemberAuthType;

public class AuthVO {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private MemberAuthType auth;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUserId() {
        return userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public MemberAuthType getAuth() {
        return auth;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setAuth(MemberAuthType auth) {
        this.auth = auth;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", auth=").append(auth);
        sb.append("]");
        return sb.toString();
    }
}