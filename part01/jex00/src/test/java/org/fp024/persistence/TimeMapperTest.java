package org.fp024.persistence;

import org.fp024.config.RootConfig;
import org.fp024.mapper.TimeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Slf4j
public class TimeMapperTest {
	@Autowired
	private TimeMapper timeMapper;
	
	@Test
	public void testGetTime() {
		logger.info(timeMapper.getClass().getName());
		logger.info(timeMapper.getTime());
	}
	
	@Test
	public void testGetTime2() {
		logger.info("getTime2");
		logger.info(timeMapper.getTime2());
	}
}