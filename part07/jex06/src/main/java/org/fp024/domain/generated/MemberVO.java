package org.fp024.domain.generated;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.fp024.type.EnabledType;

public class MemberVO {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String userPassword;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String userName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime registerDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime updateDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private EnabledType enabled;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUserId() {
        return userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUserPassword() {
        return userPassword;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUserName() {
        return userName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public EnabledType getEnabled() {
        return enabled;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEnabled(EnabledType enabled) {
        this.enabled = enabled;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userName=").append(userName);
        sb.append(", registerDate=").append(registerDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", enabled=").append(enabled);
        sb.append("]");
        return sb.toString();
    }
}