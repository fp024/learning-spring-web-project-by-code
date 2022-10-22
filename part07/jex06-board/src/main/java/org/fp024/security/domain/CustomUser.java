package org.fp024.security.domain;

import lombok.Getter;
import org.fp024.domain.MemberVO;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {
  /**
   * Spring Security 클래스에 대한 전역 직렬화 값입니다. <br>
   * 주의. 클래스는 다른 버전 간에 직렬화할 수 없습니다. <br>
   * 여전히 직렬 버전이 필요한 이유는 SEC-1709를 참조하십시오.
   */
  private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  @Getter private final MemberVO member;

  public CustomUser(MemberVO vo) {
    super(
        vo.getUserId(),
        vo.getUserPassword(),
        vo.getAuthList().stream()
            .map(auth -> new SimpleGrantedAuthority(auth.getAuth().name()))
            .toList());

    this.member = vo;
  }
}
