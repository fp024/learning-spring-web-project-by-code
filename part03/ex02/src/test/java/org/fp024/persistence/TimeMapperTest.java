package org.fp024.persistence;

import org.fp024.mapper.TimeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
class TimeMapperTest {
	@Autowired
	private TimeMapper timeMapper;
	
	@Test
	void testGetTime() {
		LOGGER.info(timeMapper.getClass().getName());
		LOGGER.info(timeMapper.getTime());
	}
	
	@Test
	void testGetTime2() {
		LOGGER.info("getTime2");
		LOGGER.info(timeMapper.getTime2());
	}
}