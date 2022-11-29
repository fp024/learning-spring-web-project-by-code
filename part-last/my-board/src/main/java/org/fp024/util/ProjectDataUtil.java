package org.fp024.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

/** Project Data 프러퍼티 관리 유틸리티 */
@Slf4j
public class ProjectDataUtil {
  /** 프로퍼티 파일은 한번만 로드 */
  private static final Properties PROPERTIES = ProjectDataUtilsHolder.PROPERTIES;

  /** Properties 를 외부로 공개하지 말고, 키로 값을 가져올 수 있는 getProperty 메서드만 추가해주자! */
  public static String getProperty(String key) {
    return PROPERTIES.getProperty(key);
  }

  public static int getIntegerProperty(String key) {
    return Integer.parseInt(getProperty(key));
  }

  public static long getLongProperty(String key) {
    return Long.parseLong(getProperty(key));
  }

  private static class ProjectDataUtilsHolder {
    private static final String PROPERTIES_FILENAME = "project-data.properties";

    private static final Properties PROPERTIES;

    static {
      Properties prop = new Properties();

      try (InputStream reader = Resources.getResourceAsStream(PROPERTIES_FILENAME)) {
        prop.load(reader);
      } catch (IOException e) {
        throw new IllegalStateException("### " + PROPERTIES_FILENAME + " 프로퍼티 파일 로드 실패 ㅠㅠ", e);
      }
      PROPERTIES = prop;
      LOGGER.info("### {} 프로퍼티 파일 로드 성공!!! ^^", PROPERTIES_FILENAME);
    }
  }
}
