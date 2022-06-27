package org.fp024.security;

import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(classes = {RootConfig.class, SecurityConfig.class})
public class PasswordEncoderTest {

  @Autowired private PasswordEncoder passwordEncoder;

  @Test
  public void testEncode() {
    String text = "member";

    String encodedText = passwordEncoder.encode(text);

    LOGGER.info("encoded text: {}", encodedText);
  }
}
