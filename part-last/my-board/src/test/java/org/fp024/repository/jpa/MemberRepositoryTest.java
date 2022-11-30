package org.fp024.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.domain.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
class MemberRepositoryTest {
  @Autowired private MemberRepository repository;

  @Test
  void testFindById() {
    Optional<MemberVO> memberVOOptional = repository.findById("admin90");
    assertThat(memberVOOptional.isPresent()).isTrue();

    assertThat(memberVOOptional.get()) //
        .hasFieldOrPropertyWithValue("userId", "admin90")
        .hasFieldOrPropertyWithValue("userName", "관리자90");
  }
}
