package org.fp024.persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fp024.config.RootConfig;
import org.fp024.mapper.TimeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@Slf4j
class TimeMapperTest {
  @Autowired private TimeMapper timeMapper;

  @Test
  void testGetTime() {
    LOGGER.info(timeMapper.getClass().getName());
    LOGGER.info(timeMapper.getTime());
    assertTrue(true);
  }

  @Test
  void testGetTime2() {
    LOGGER.info("getTime2");
    LOGGER.info(timeMapper.getTime2());
    assertTrue(true);
  }
}
