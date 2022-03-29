package org.fp024.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  @Transactional
  @Override
  public void addData(String value) {
    LOGGER.info("mapper1 .....");
    mapper1.insertCol1(value);

    LOGGER.info("mapper2 .....");
    mapper2.insertCol2(value);

    LOGGER.info("end .....");
  }
}
