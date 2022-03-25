package org.fp024.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
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
    assertThrows(
        NumberFormatException.class, () -> LOGGER.info(service.doAdd("123", "ABC").toString()));
  }
}
