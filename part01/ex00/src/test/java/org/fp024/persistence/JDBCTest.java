package org.fp024.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCTest {
	private final static Logger LOGGER = LoggerFactory.getLogger(JDBCTest.class);

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Test
	public void testConnection() {
		String url = "jdbc:oracle:thin:@192.168.137.152:1521:XE";
		try (Connection con = DriverManager.getConnection(url, "book_ex", "book_ex")) {
			logger.info("{}", con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
