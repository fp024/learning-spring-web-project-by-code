package org.fp024.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

  @Column(nullable = false)
  private Long bno;

  /** DB 저장은 항상 Unix Path 기준으로 저장됨 */
  @Column(length = 200, nullable = false, name = "uploadpath")
  private String uploadPath;

  @Column(length = 200, nullable = false, name = "filename")
  private String fileName;

  @Column(length = 1, name = "filetype")
  @Convert(converter = FileTypeConverter.class)
  private FileType fileType;
}
