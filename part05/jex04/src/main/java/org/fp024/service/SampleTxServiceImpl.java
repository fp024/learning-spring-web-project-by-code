package org.fp024.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.Sample1;
import org.fp024.domain.Sample2;
import org.fp024.mapper.Sample1Mapper;
import org.fp024.mapper.Sample2Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SampleTxServiceImpl implements SampleTxService {
  private final Sample1Mapper mapper1;
  private final Sample2Mapper mapper2;

  /*
   * 컬럼 1개만 있는 단순 테이블이지만, mybatis-dynamic-sql으로 다루기위해 도메인으로 감싸게 되었다.
   */
  @Transactional
  @Override
  public void addData(String value) {
    LOGGER.info("mapper1 .....");
    Sample1 sample1 = new Sample1();
    sample1.setCol1(value);
    mapper1.insert(sample1);

    LOGGER.info("mapper2 .....");
    Sample2 sample2 = new Sample2();
    sample2.setCol2(value);
    mapper2.insert(sample2);

    LOGGER.info("end .....");
  }
}
