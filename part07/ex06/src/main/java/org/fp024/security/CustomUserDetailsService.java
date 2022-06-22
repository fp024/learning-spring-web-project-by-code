package org.fp024.security;

import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.MemberVO;
import org.fp024.mapper.MemberMapper;
import org.fp024.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired private MemberMapper memberMapper;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    LOGGER.warn("Load User by userName: {}", userName);

    MemberVO member = memberMapper.read(userName);

    LOGGER.warn("queried by member mapper: {}", member);

    if (member == null) {
      throw new UsernameNotFoundException("userName: " + userName);
    }
    return new CustomUser(member);
  }
}
