package org.fp024.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;

public class ReplyVO {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long rno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long bno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String reply;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String replyer;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime replyDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime updateDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getRno() {
        return rno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRno(Long rno) {
        this.rno = rno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getBno() {
        return bno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setBno(Long bno) {
        this.bno = bno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getReply() {
        return reply;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getReplyer() {
        return replyer;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setReplyer(String replyer) {
        this.replyer = replyer == null ? null : replyer.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getReplyDate() {
        return replyDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setReplyDate(LocalDateTime replyDate) {
        this.replyDate = replyDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rno=").append(rno);
        sb.append(", bno=").append(bno);
        sb.append(", reply=").append(reply);
        sb.append(", replyer=").append(replyer);
        sb.append(", replyDate=").append(replyDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append("]");
        return sb.toString();
    }
}