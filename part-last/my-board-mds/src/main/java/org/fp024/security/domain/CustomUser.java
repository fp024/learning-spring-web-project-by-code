package org.fp024.security.domain;

import java.io.Serial;
import lombok.Getter;
import org.fp024.domain.MemberDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {
  /**
   * Spring Security 클래스에 대한 전역 직렬화 값입니다. <br>
   * 주의. 클래스는 다른 버전 간에 직렬화할 수 없습니다. <br>
   * 여전히 직렬 버전이 필요한 이유는 SEC-1709를 참조하십시오.
   */
  @Serial private static final long serialVersionUID = -7527477803279659303L;

  @Getter private final MemberDTO member;

  public CustomUser(MemberDTO vo) {
    super(
        vo.getMemberVO().getUserId(),
        vo.getMemberVO().getUserPassword(),
        vo.getAuthList().stream()
            .map(auth -> new SimpleGrantedAuthority(auth.getAuth().name()))
            .toList());

    this.member = vo;
  }
}
