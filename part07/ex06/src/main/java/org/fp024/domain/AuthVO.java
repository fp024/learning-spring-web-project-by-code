package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.fp024.type.MemberAuthType;

/** 회원 권한 도메인 */
@Getter
@Setter
@ToString
@Alias("authVO")
public class AuthVO {
  private String userId;
  private MemberAuthType auth;
}
