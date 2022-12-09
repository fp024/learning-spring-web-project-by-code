package org.fp024.task;

import org.fp024.config.RootConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
class FileCheckTaskTest {
  @Autowired FileCheckTask task;

  @Test
  void test() {
    task.checkFiles();
  }

  @Test
  void getOldFiles() {
    task.getOldFiles();
  }
}
