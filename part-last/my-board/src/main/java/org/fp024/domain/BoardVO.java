package org.fp024.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

@NoArgsConstructor // 컴파일 에러 회피
@ToString
@Getter
@Setter // 컴파일 에러 회피
@Entity
@DynamicInsert // INSERT 할때 null 인 경우 쿼리에 포함시키지 않음.
@Table(name = "tbl_board")
public class BoardVO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bno;

  @Column(length = 200, nullable = false)
  private String title;

  @Column(length = 2000, nullable = false)
  private String content;

  @Column(length = 50, nullable = false)
  private String writer;

  private LocalDateTime regdate;

  @Column(name = "updatedate")
  private LocalDateTime updateDate;

  @Column(name = "replycnt")
  private int replyCount;

  @Transient private List<BoardAttachVO> attachList;
}
