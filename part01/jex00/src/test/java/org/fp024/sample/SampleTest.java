package org.fp024.sample;

import static org.junit.Assert.assertNotNull;

import org.fp024.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Slf4j
public class SampleTest {
	
	// JDK 버전에 따라 달라질 수 도 있는 부분이여서, 처음부터 필드 위에 @Autowired를 붙이거나
	// setter를 따로 만들어서 가이드 주는게 나아보인다.
	@Setter(onMethod_= {@Autowired})
	private Restaurant restaurant;
	
	@Test
	public void testExist() {
		assertNotNull(restaurant);

		logger.info("{}", restaurant);
		logger.info("------------------------------------------");
		logger.info("{}", restaurant.getChef());
	}
	
}
