package org.fp024.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.MemberVO;
import org.fp024.security.domain.CustomUser;
import org.fp024.service.MemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final MemberService memberService;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    LOGGER.warn("Load User by userName: {}", userName);
    Optional<MemberVO> optionalMember = memberService.read(userName);
    MemberVO member =
        optionalMember.orElseThrow(() -> new UsernameNotFoundException("userName: " + userName));
    return new CustomUser(member);
  }
}
