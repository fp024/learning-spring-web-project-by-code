package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fp024.type.MemberAuthType;

/** 권한 VO */
@Getter
@Setter
@ToString
public class AuthVO {
  private String userId;
  private MemberAuthType auth;
}
