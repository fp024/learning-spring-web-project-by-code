package org.fp024.helper;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

public class CustomBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {
  public CustomBeanPropertySqlParameterSource(Object object) {
    super(object);
  }

  /** enum 인식을 위해 getValue() 메서드를 오버라이드 */
  @Override
  public Object getValue(String paramName) throws IllegalArgumentException {
    Object value = super.getValue(paramName);
    if (value instanceof Enum) {
      return value.toString();
    }
    return value;
  }
}
