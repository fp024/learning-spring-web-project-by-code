package org.fp024.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor // TODO: 컴파일 오류 방지
@ToString
@Getter
@Setter // TODO: 컴파일 오류 방지
@Entity
@Table(name = "tbl_reply")
public class ReplyVO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rno;

  @Column(nullable = false)
  private Long bno;

  @Column(length = 1000, nullable = false)
  private String reply;

  @Column(length = 50, nullable = false)
  private String replyer;

  @Column(name = "replydate")
  private LocalDateTime replyDate;

  @Column(name = "updatedate")
  private LocalDateTime updateDate;
}
