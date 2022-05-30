package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/** 첨부파일 VO */
@Getter
@Setter
@ToString
@Alias("boardAttachVO")
public class BoardAttachVO {
  /** UUID */
  private String uuid;

  /** 업로드 경로 */
  private String uploadPath;

  /** 파일 명 */
  private String fileName;

  /** 파일 형식 */
  private FileType fileType;

  /** 게시물 번호 */
  private Long bno;
}
