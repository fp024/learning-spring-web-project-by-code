package org.fp024.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder {

  /**
   * 원문 패스워드를 인코딩하지 않고 그대로 반환
   *
   * @param rawPassword 원문 패스워드
   * @return 원문 패스워드 문자열
   */
  @Override
  public String encode(CharSequence rawPassword) {
    LOGGER.warn("before encode: {}", rawPassword);
    return rawPassword.toString();
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    LOGGER.warn("matches: {} : {}", rawPassword, encodedPassword);
    return rawPassword.toString().equals(encodedPassword);
  }
}
