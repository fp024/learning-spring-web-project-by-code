package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** 회원 권한 도메인 */
@Getter
@Setter
@ToString
public class MemberAuth {
  private String userId;
  private MemberAuthType auth;
}
