package org.fp024.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/** νμ κΆν VO */
@Getter
@Setter
@ToString
@Alias("authVO")
public class AuthVO {
  private String userId;
  private MemberAuthType auth;
}
