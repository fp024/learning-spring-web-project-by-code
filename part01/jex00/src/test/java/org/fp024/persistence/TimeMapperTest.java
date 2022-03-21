package org.fp024.persistence;

import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.mapper.TimeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
class TimeMapperTest {
  @Autowired private TimeMapper timeMapper;

  @Test
  void testGetTime() {
    logger.info(timeMapper.getClass().getName());
    logger.info(timeMapper.getTime());
  }

  @Test
  void testGetTime2() {
    logger.info("getTime2");
    logger.info(timeMapper.getTime2());
  }
}
