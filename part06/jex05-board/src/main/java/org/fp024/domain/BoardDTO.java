package org.fp024.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
  private BoardVO boardVO;

  /** 첨부파일 목록 */
  private List<BoardAttachVO> attachList;
}
