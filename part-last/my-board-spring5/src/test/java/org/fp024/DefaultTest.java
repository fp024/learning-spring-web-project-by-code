package org.fp024;

import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
public class DefaultTest {

  @DisplayName("Context 로드 테스트")
  @Test
  void testContextLoad() {}
}
