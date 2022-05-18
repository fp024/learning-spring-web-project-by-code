package org.fp024.domain;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
