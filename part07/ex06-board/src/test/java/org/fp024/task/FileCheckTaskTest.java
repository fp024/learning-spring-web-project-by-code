package org.fp024.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
class FileCheckTaskTest {

  @Autowired FileCheckTask task;

  @Test
  void test() {
    task.checkFiles();
  }
}
