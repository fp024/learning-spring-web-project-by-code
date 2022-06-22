package org.fp024.helper;

import lombok.extern.slf4j.Slf4j;
import org.fp024.type.CodeOperation;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

@Slf4j
public class CustomBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {
  public CustomBeanPropertySqlParameterSource(Object object) {
    super(object);
  }

  /** enum 인식을 위해 getValue() 메서드를 오버라이드 */
  @Override
  public Object getValue(String paramName) throws IllegalArgumentException {
    Object value = super.getValue(paramName);

    // code 기반으로 설정된 Enum이면 code 값을 DB에 저장.
    if (value instanceof CodeOperation) {
      return ((CodeOperation) value).getCode();
    }

    // 그렇지 않고 일반 Enum이면 enum의 이름 문자열을 DB에 저장.
    if (value instanceof Enum) {
      return value.toString();
    }
    return value;
  }
}
