package org.fp024.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProjectDataUtilTest {

  @Test
  void testGetProperty() {
    String uploadTempFolder = ProjectDataUtil.getProperty("multipart.uploadTempFolder");
    assertThat(uploadTempFolder).isNotEmpty();
    String uploadFolder = ProjectDataUtil.getProperty("multipart.uploadFolder");
    assertThat(uploadFolder).isNotEmpty();

    switch (ProjectDataUtil.getProperty("multipart.env")) {
      case "win" -> {
        assertThat(uploadFolder).isEqualTo("C:\\upload");
        assertThat(uploadTempFolder).isEqualTo("C:\\upload\\temp");
      }
      case "linux" -> {
        assertThat(uploadFolder).isEqualTo("/home/fp024/upload");
        assertThat(uploadTempFolder).isEqualTo("/home/fp024/upload/temp");
      }

      default -> throw new IllegalStateException(
          "Unexpected value: " + ProjectDataUtil.getProperty("multipart.env"));
    }
  }

  @Test
  void testGetIntProperty() {
    int fileSizeThreshold = ProjectDataUtil.getIntProperty("multipart.fileSizeThreshold");
    assertThat(fileSizeThreshold).isEqualTo(20971520);
  }

  @Test
  void testGetLongProperty() {
    long maxFileSize = ProjectDataUtil.getLongProperty("multipart.maxFileSize");
    assertThat(maxFileSize).isEqualTo(20971520L);
    long maxRequestSize = ProjectDataUtil.getLongProperty("multipart.maxRequestSize");
    assertThat(maxRequestSize).isEqualTo(41943040L);
  }
}
