package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachFileDTO {
  private String fileName;
  private String uploadPath;
  private String uuid;
  private boolean image;
  private boolean success;
}
