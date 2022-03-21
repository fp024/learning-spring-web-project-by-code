package org.fp024.persistence;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
class JDBCTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(JDBCTest.class);

  static {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  void testConnection() {
    String url = "jdbc:oracle:thin:@localvmdb.oracle_xe_18c:1521:XE";
    try (Connection con = DriverManager.getConnection(url, "book_ex", "book_ex")) {
      logger.info("{}", con);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
