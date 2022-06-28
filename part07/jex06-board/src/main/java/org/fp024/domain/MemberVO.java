package org.fp024.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 VO
 *
 * <p>MyBatis Generator로 자동생성했지만, 1:N 관계를 나타내는 AuthVO 리스트 필드 추가 때문에, 코드를 수정했는데, <br>
 * 이미 수동 수정할 내용... lombok 쓰는 코드로 바꿔는 것이 낫겠다.
 */
@Getter
@Setter
@ToString
public class MemberVO {
  private String userId;
  private String userPassword;
  private String userName;
  private LocalDateTime registerDate;
  private LocalDateTime updateDate;
  private EnabledType enabled;
  private List<AuthVO> authList;
}
