package org.fp024.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Jackson ObjectMapper 헬퍼 클래스 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperHelper {
  public static ObjectMapper objectMapper() {
    return ObjectMapperHolder.INSTANCE;
  }

  private static class ObjectMapperHolder {
    private static final ObjectMapper INSTANCE =
        new ObjectMapper().registerModule(new JavaTimeModule());
  }
}
