package org.fp024.persistence;

import lombok.extern.slf4j.Slf4j;
import org.fp024.mapper.TimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ContextConfiguration()
@SpringJUnitConfig(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
class TimeMapperTest {
  @Autowired private TimeMapper timeMapper;

  public void testGetTime() {
    logger.info(timeMapper.getClass().getName());
    logger.info(timeMapper.getTime());
  }

  public void testGetTime2() {
    logger.info("getTime2");
    logger.info(timeMapper.getTime2());
  }
}
