package org.fp024.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** 회원 도메인 */
@Getter
@Setter
@ToString
public class Member {
  private String userId;
  private String userPassword;
  private String userName;
  private LocalDateTime registerDate;
  private LocalDateTime updateDate;
  private String enabled;
}
