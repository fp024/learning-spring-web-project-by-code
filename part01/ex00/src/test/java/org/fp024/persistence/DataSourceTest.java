package org.fp024.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTest {
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void testConnection() {
		//  oracle.jdbc.driver.OracleDriver 을 사용할 때의 경고 로그
		// 	WARN : com.zaxxer.hikari.util.DriverDataSource - 
		//		   Registered driver with driverClassName=oracle.jdbc.driver.OracleDriver was not found, 
		// 		   trying direct instantiation.
		
		// Oracle 9 이후로... 
		// oracle.jdbc.driver.OracleDriver 대신에 oracle.jdbc.OracleDriver를 사용해야한다고 함.
		
		try (Connection con = dataSource.getConnection()) {
			logger.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
