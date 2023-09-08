package org.fp024;

import org.fp024.config.RootConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {RootConfig.class})
public class DefaultTest {

  @DisplayName("Context 로드 테스트")
  @Test
  void testContextLoad() {}
}
