package org.springframework.core;

import java.io.IOException;
import org.springframework.lang.Nullable;

/**
 * TODO: mybatis-spring 사용처가 완전히 제거되면 이 클래스도 제거하면 된다.
 * Spring 6에서 이 클래스가 제거되었는데... 사용처가 있는지? 프로그램 시작때 ClassNotFound 예외로 빈들이 생성되지 않는다.
 *
 * <p>이 클래스를 프로젝트에 강제로 추가해주었을 때, 해결은 하였다.
 * https://github.com/spring-projects/spring-framework/commit/2fb1dd177b7b056f30a9de0739d8afdef37d72aa?diff=unified#diff-cae26b2a33fc578495f60916876a609d0270cf0ef6680944a2bce9d5795cbcb2
 */
public class NestedIOException extends IOException {

  static {
    // Eagerly load the NestedExceptionUtils class to avoid classloader deadlock
    // issues on OSGi when calling getMessage(). Reported by Don Brown; SPR-5607.
    NestedExceptionUtils.class.getName();
  }

  /**
   * Construct a {@code NestedIOException} with the specified detail message.
   *
   * @param msg the detail message
   */
  public NestedIOException(String msg) {
    super(msg);
  }

  /**
   * Construct a {@code NestedIOException} with the specified detail message and nested exception.
   *
   * @param msg the detail message
   * @param cause the nested exception
   */
  public NestedIOException(@Nullable String msg, @Nullable Throwable cause) {
    super(msg, cause);
  }

  /** Return the detail message, including the message from the nested exception if there is one. */
  @Override
  @Nullable
  public String getMessage() {
    return NestedExceptionUtils.buildMessage(super.getMessage(), getCause());
  }
}
