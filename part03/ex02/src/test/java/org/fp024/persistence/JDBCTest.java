package org.fp024.persistence;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class JDBCTest {
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
			LOGGER.info("{}", con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
