package org.fp024.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 유틸리티 클래스
 *
 * <p>컨트롤러에서 분리할 수 있는 유틸리티성 로직은 여기에 몰아넣자.
 */
public class CommonUtil {
  public static String getFolder() {
    return LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        .replace("-", File.separator);
  }

  public static String getUUID() {
    return UUID.randomUUID().toString();
  }
}
