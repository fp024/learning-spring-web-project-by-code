package org.fp024.sample;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
public class SampleHotelTest {

  @Autowired private SampleHotel hotel;

  @Test
  public void testExist() {
    // 필드명에 @Nonulll 어노터이션을 붙이고 생성자를 만들면
    // 필드 자체를 final로 생성하진 않으나 null 검사 로직이 들어간다.
    assertThrows(NullPointerException.class, () -> new SampleHotel(null));

    assertNotNull(hotel);
    logger.info("-----------------------------------");
    logger.info("{}", hotel.getChef());
  }
}
