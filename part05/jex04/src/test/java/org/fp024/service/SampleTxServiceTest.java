package org.fp024.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = RootConfig.class)
@Slf4j
class SampleTxServiceTest {

  @Autowired private SampleTxService service;

  @Test
  void testLong() {
    String str =
        "Starry\r\n"
            + "Starry night\r\n"
            + "Paint your palette blue and grey\r\n"
            + "Look out on a summer's day";

    // 자동으로 인코딩값을 넣어준다. 내가 사용하는 환경에 전부 UTF-8이여서, 명시적으로 지정하는게 낫긴한 것 같음.
    // 그런데, 전부 한글이 들어가진 않아서, 차이는 없을 것 같다.
    LOGGER.info("str의 문자길이: {}", str.getBytes(StandardCharsets.UTF_8).length);

    // Oracle에서는 UncategorizedSQLException 예외로 처리되지만,
    // HyperSQL DB에서는 DataIntegrityViolationException 으로 반환된다.

    assertThrows(
        DataIntegrityViolationException.class,
        () -> {
          service.addData(str);
        });
  }
}
