package org.fp024.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.fp024.util.GsonHelper;
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

  @Column(nullable = false)
  private String content;

  @Column(length = 50, nullable = false)
  private String writer;

  private LocalDateTime regdate;

  @Column(name = "updatedate")
  private LocalDateTime updateDate;

  @Column(name = "replycnt")
  private int replyCount;

  @Transient private List<BoardAttachVO> attachList;

  public String getJsonContent() {
    return GsonHelper.toJson(content);
  }
}
