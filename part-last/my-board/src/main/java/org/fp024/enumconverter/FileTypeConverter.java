package org.fp024.enumconverter;

import jakarta.persistence.AttributeConverter;
import java.util.stream.Stream;
import org.fp024.domain.FileType;

/** TODO: 컨버터 모양이 동일하게 되어버렸는데... 나중에 공통화하면 좋겠다. */
public class FileTypeConverter implements AttributeConverter<FileType, String> {
  @Override
  public String convertToDatabaseColumn(FileType fileType) {
    if (fileType == null) {
      return null;
    }
    return fileType.getCode();
  }

  @Override
  public FileType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return Stream.of(FileType.values())
        .filter(grade -> grade.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    FileType.class.getCanonicalName() + "에 정의되지 않은 않는 코드: " + dbData));
  }
}
