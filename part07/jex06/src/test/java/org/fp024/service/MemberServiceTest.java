package org.fp024.service;

import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.domain.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringJUnitConfig(classes = {RootConfig.class})
class MemberServiceTest {

  @Autowired private MemberService memberService;

  @Test
  void testRead() {
    Optional<MemberVO> member = memberService.read("admin90");

    assertTrue(member.isPresent());

    LOGGER.info("{}", member.get());
  }
}
