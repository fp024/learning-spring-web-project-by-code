package org.fp024.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.fp024.enumconverter.FileTypeConverter;

@NoArgsConstructor // TODO: 컴파일 오류 방지
@ToString
@Getter
@Setter // TODO: 컴파일 오류 방지
@Entity
@Table(name = "tbl_attach")
public class BoardAttachVO {
  @Id
  @Column(length = 100)
  private String uuid;

  @Column(length = 200, name = "uploadpath")
  private String uploadPath;

  @Column(length = 200, name = "filename")
  private String fileName;

  @Column(length = 1, name = "filetype")
  @Convert(converter = FileTypeConverter.class)
  private FileType fileType;

  @JoinColumn(name = "bno")
  @ManyToOne
  private BoardVO boardVO;

  // TODO: 컴파일 오류 방지
  public Long getBno() {
    return boardVO.getBno();
  }
  // TODO: 컴파일 오류 방지
  public void setBno(Long bno) {
    boardVO.setBno(bno);
  }
}
