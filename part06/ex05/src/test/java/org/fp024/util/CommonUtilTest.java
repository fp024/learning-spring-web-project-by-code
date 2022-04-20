package org.fp024.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.io.File;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class CommonUtilTest {
  @Test
  void testGetFolder() {
    // Mock 적용 외부에서 미리 기대 날짜를 만들기.
    LocalDateTime currentDateTime = LocalDateTime.of(2022, 4, 20, 0, 0, 0, 0);

    try (MockedStatic<LocalDateTime> mockedLocalDateTime =
        mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
      mockedLocalDateTime.when(() -> LocalDateTime.now()).thenReturn(currentDateTime);

      assertEquals("2022" + File.separator + "04" + File.separator + "20", CommonUtil.getFolder());
    }
  }
}
