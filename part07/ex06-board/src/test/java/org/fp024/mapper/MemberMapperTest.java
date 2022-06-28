package org.fp024.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.EnabledType;
import org.fp024.domain.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(
    locations = {
      "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    })
class MemberMapperTest {
  @Autowired private MemberMapper mapper;

  @Test
  void testRead() {
    MemberVO member = mapper.read("admin90");
    LOGGER.info("member: {}", member);

    assertEquals(EnabledType.YES, member.getEnabled());
  }
}
