package org.fp024.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 * 유틸리티 클래스
 *
 * <p>컨트롤러에서 분리할 수 있는 유틸리티성 로직은 여기에 몰아넣자.
 */
@Slf4j
public class CommonUtil {
  public static String getFolder() {
    return LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        .replace("-", File.separator);
  }

  public static String getUUID() {
    return UUID.randomUUID().toString();
  }

  /** 이미지 파일 판단 */
  public static boolean checkImageType(File file) {
    try {
      String contentType = Files.probeContentType(file.toPath());
      return contentType.startsWith("image");
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return false;
  }
}
