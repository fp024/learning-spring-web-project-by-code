package org.fp024.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor // 컴파일 에러 회피
@ToString
@Getter
@Setter // 컴파일 에러 회피
@Entity
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

  @OneToMany(mappedBy = "boardVO")
  private List<BoardAttachVO> attachList;
}
