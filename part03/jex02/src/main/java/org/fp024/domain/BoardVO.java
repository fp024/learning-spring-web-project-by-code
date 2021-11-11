package org.fp024.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import org.apache.ibatis.type.Alias;

@Alias("boardVO")
public class BoardVO {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long bno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String title;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String content;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String writer;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime regdate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime updateDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getBno() {
        return bno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setBno(Long bno) {
        this.bno = bno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTitle() {
        return title;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getContent() {
        return content;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getWriter() {
        return writer;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setWriter(String writer) {
        this.writer = writer == null ? null : writer.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getRegdate() {
        return regdate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
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
        sb.append(", bno=").append(bno);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", writer=").append(writer);
        sb.append(", regdate=").append(regdate);
        sb.append(", updateDate=").append(updateDate);
        sb.append("]");
        return sb.toString();
    }
}