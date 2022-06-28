package org.fp024.domain;

import javax.annotation.Generated;

public class BoardAttachVO {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String uuid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String uploadPath;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String fileName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private FileType fileType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long bno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUuid() {
        return uuid;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUploadPath() {
        return uploadPath;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath == null ? null : uploadPath.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getFileName() {
        return fileName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public FileType getFileType() {
        return fileType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getBno() {
        return bno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setBno(Long bno) {
        this.bno = bno;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", uploadPath=").append(uploadPath);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileType=").append(fileType);
        sb.append(", bno=").append(bno);
        sb.append("]");
        return sb.toString();
    }
}