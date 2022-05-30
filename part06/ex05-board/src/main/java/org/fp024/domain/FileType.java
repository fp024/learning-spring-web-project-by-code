package org.fp024.domain;

import lombok.Getter;

/** 파일 타입 */
public enum FileType {
  NORMAL("N"),
  IMAGE("I");

  @Getter private final String code;

  FileType(String code) {
    this.code = code;
  }

  public static FileType valueByCode(String code) {
    for (FileType fileType : FileType.values()) {
      if (fileType.code.equals(code)) {
        return fileType;
      }
    }
    throw new IllegalArgumentException("잘못된 파일 타입 코드 입니다. 코드:" + code);
  }
}
