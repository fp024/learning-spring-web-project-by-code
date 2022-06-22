package org.fp024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fp024.type.CodeOperation;
import org.fp024.type.EnabledType;
import org.fp024.type.MemberAuthType;
import org.junit.jupiter.api.Test;

/** MyBatis Enum 타입 핸들러 재정의 클래스 변경하기전 Enum 동작 테스트 */
public class EnumTest {
  class ETest<E extends Enum<E>> {
    final Class<E> type;

    ETest(Class<E> type) {
      this.type = type;
    }

    public E getEnum(String s) {
      try {
        E[] values = type.getEnumConstants();
        if (values[0] instanceof CodeOperation) {
          for (E e : values) {
            if (((CodeOperation) e).getCode().equals(s)) {
              return e;
            }
          }
          throw new IllegalStateException("저장소 enum code가 잘못되었는지 확인 필요");
        }
        return Enum.valueOf(type, s);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Test
  void testGetEnum() {
    ETest<EnabledType> e = new ETest<>(EnabledType.class);

    assertEquals(EnabledType.YES, e.getEnum("Y"));
    assertEquals(EnabledType.NO, e.getEnum("N"));

    ETest<MemberAuthType> m = new ETest<>(MemberAuthType.class);

    assertEquals(MemberAuthType.ROLE_ADMIN, m.getEnum("ROLE_ADMIN"));
    assertEquals(MemberAuthType.ROLE_MEMBER, m.getEnum("ROLE_MEMBER"));
  }
}
