package org.fp024.service;

import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = RootConfig.class)
@Slf4j
class SampleServiceTest {
  @Autowired private SampleService service;

  @Test
  void testClass() {
    LOGGER.info(service.toString());
    LOGGER.info(service.getClass().getName());
  }

  @Test
  void testAdd() {
    LOGGER.info("{}", service.doAdd("123", "456"));
  }

  @Test
  void testAddError() {
    // AOP 코드 상에서 예외 발생시 예외만 잡고, 그것을 따로 던지질 않아서 예외가 던져지지 않는다.
    service.doAdd("123", "ABC");
  }
}
