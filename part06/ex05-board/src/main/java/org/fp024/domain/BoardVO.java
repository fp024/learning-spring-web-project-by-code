package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Alias("boardVO")
public class BoardVO {
  private Long bno;
  private String title;
  private String content;
  private String writer;
  private LocalDateTime regdate;
  private LocalDateTime updateDate;

  private int replyCount;

  private List<BoardAttachVO> attachList;
}
