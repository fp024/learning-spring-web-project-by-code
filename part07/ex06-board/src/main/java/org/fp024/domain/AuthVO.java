package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/** 회원 권한 VO */
@Getter
@Setter
@ToString
@Alias("authVO")
public class AuthVO {
  private String userId;
  private MemberAuthType auth;
}
