package org.fp024.sample;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
class SampleTest {

  @Setter(onMethod_ = {@Autowired})
  private Restaurant restaurant;

  void testExist() {
    assertNotNull(restaurant);

    logger.info("{}", restaurant);
    logger.info("------------------------------------------");
    logger.info("{}", restaurant.getChef());
  }
}
