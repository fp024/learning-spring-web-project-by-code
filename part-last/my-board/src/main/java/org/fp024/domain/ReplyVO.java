package org.fp024.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
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
