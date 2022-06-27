package org.fp024.type;

import lombok.Getter;

/** Enum이름 그대로 DB에 저장하기 때문에, CodeOperation을 구현하진 않았다. */
public enum MemberAuthType {
  ROLE_USER("USER"),
  ROLE_MEMBER("MEMBER"),
  ROLE_ADMIN("ADMIN");

  @Getter() private String roleUserName;

  MemberAuthType(String roleUserName) {
    this.roleUserName = roleUserName;
  }
}
