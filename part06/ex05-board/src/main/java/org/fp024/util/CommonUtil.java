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

  /** Windows 경로를 Unix 경로로 변환 */
  public static String winPathToUnixPath(String path) {
    return path.replace("\\", "/");
  }

  /**
   * Unix 경로를 현재 실행 시스탬 경로 구분자로 변환
   *
   * <p>DB에서의 경로는 항상 UNIX 경로 구분자로 저장하기로 했으므로, DB에서 불러온 업로드 경로는 사용할 시점에 현재 시스템 경로에 맞게 바꿔서 쓴다.
   */
  public static String unixPathToCurrentSystemPath(String path) {
    return path.replace("/", File.separator);
  }
}
