package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReplyVO {
  private Long rno;
  private Long bno;

  private String reply;
  private String replyer;
  private LocalDateTime replyDate;
  private LocalDateTime updateDate;
}
