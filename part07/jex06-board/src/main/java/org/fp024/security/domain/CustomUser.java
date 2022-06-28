package org.fp024.security.domain;

import lombok.Getter;
import org.fp024.domain.MemberVO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

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
