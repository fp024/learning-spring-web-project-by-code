package org.fp024.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/** 회원 도메인 */
@Getter
@Setter
@ToString
@Alias("memberVO")
public class MemberVO {
  private String userId;
  private String userPassword;
  private String userName;
  private EnabledType enabled;

  private LocalDateTime registerDate;
  private LocalDateTime updateDate;

  private List<AuthVO> authList;
}
