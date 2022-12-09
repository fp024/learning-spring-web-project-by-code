package org.fp024.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

@NoArgsConstructor // TODO: 컴파일 오류 방지
@ToString
@Getter
@Setter // TODO: 컴파일 오류 방지
@Entity
@DynamicInsert // INSERT 할때 null 인 경우 쿼리에 포함시키지 않음.
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
