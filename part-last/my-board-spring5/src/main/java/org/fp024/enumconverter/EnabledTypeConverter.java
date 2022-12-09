package org.fp024.enumconverter;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;
import org.fp024.domain.EnabledType;

/** TODO: 컨버터 모양이 동일하게 되어버렸는데... 나중에 공통화하면 좋겠다. */
public class EnabledTypeConverter implements AttributeConverter<EnabledType, String> {
  @Override
  public String convertToDatabaseColumn(EnabledType fileType) {
    if (fileType == null) {
      return null;
    }
    return fileType.getCode();
  }

  @Override
  public EnabledType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return Stream.of(EnabledType.values())
        .filter(grade -> grade.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    EnabledType.class.getCanonicalName() + "에 정의되지 않은 않는 코드: " + dbData));
  }
}
