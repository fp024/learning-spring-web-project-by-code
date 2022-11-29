package org.fp024.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "tbl_member")
public class MemberVO {
  @Id
  @Column(length = 50, name = "userid")
  private String userId;

  @Column(length = 100, name = "userpw", nullable = false)
  private String userPassword;

  @Column(length = 100, name = "username", nullable = false)
  private String userName;

  private LocalDateTime registerDate;
  private LocalDateTime updateDate;
  private EnabledType enabled;

  @OneToMany(mappedBy = "memberVO", fetch = FetchType.EAGER)
  private List<AuthVO> authList;
}
